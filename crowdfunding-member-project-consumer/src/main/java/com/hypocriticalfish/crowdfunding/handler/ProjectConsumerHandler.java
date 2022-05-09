package com.hypocriticalfish.crowdfunding.handler;

import com.hypocriticalfish.crowdfunding.api.MySQLRemoteService;
import com.hypocriticalfish.crowdfunding.config.OSSProperties;
import com.hypocriticalfish.crowdfunding.constant.CrowdConstant;
import com.hypocriticalfish.crowdfunding.entity.vo.*;
import com.hypocriticalfish.crowdfunding.util.CrowdfundingUtil;
import com.hypocriticalfish.crowdfunding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/9 14:36
 * @Description
 */
@Controller
public class ProjectConsumerHandler {

    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/get/project/detail/{projectId}")
    public String getProjectDetail(@PathVariable("projectId") Integer projectId,
                                   Model model) {

        ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVORemote(projectId);

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            DetailProjectVO detailProjectVO = resultEntity.getData();
            model.addAttribute("detailProjectVO", detailProjectVO);
        }

        return "project-show-detail";
    }


    @RequestMapping("/create/confirm")
    public String saveConfirm(HttpSession session, MemberConfirmInfoVO memberConfirmInfoVO,ModelMap modelMap) {
        // 1.从session域读取之前临时存储的projectVO 对象
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        // 2.如果为null,则抛出异常
        if (projectVO == null) {
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }

        // 3.将确认数据设置到projectVO对象中
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

        // 4.从当前session域读取当前登录的用户
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();

        // 4.调用远程的方法保存到projectPO
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectVORemote(projectVO,memberId);
        // 5.判断远程的保存操作是否成功
        if (ResultEntity.FAILED.equals(saveResultEntity.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getMessage());
            return "project-confirm";
        }

        // 6.将临时的ProjectVO对象从session域移除
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        return "redirect:http://localhost/project/create/success";
    }



    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity saveReturn(ReturnVO returnVO,HttpSession session){
        try {
            // 1.从session域中读取缓存的ProjectVO对象
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

            // 2.判断projectVO是否为null
            if (projectVO == null) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }

            // 3.从projectVO对象中获取存储回报信息的集合
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();

            // 4.判断returnVOList集合是否有效
            if (returnVOList == null || returnVOList.size() == 0) {
                returnVOList = new ArrayList<>();
                projectVO.setReturnVOList(returnVOList);
            }
            // 5.将收集了表单数据的returnVO对象存入集合
            returnVOList.add(returnVO);

            // 6.将更新后的数据存回session域中
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(
            @RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {
        // 1.执行文件上传
        ResultEntity<String> uploadReturnPicResultEntity = CrowdfundingUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                Objects.requireNonNull(returnPicture.getOriginalFilename()));

        // 2.判断上传结果成功还是失败
        return uploadReturnPicResultEntity;
    }


    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(ProjectVO projectVO,
                                       MultipartFile headerPicture,
                                       List<MultipartFile> detailPictureList,
                                       HttpSession session,
                                       ModelMap modelMap) throws IOException {

        // 一、完成头图上传
        // 1.判断头图是否为空
        boolean headerPictureIsEmpty = headerPicture.isEmpty();

        if (headerPictureIsEmpty) {

            // 2.如果没有上传头图则返回到表单页面并显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project-launch";

        }

        // 3.如果用户确实上传了有内容的文件， 则执行上传
        ResultEntity<String> uploadHeaderPicResultEntity = CrowdfundingUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                Objects.requireNonNull(headerPicture.getOriginalFilename()));

        String result = uploadHeaderPicResultEntity.getResult();

        // 4.判断头图是否上传成功
        if (ResultEntity.SUCCESS.equals(result)) {

            // 5.成功则获取图片访问路径
            String headerPicturePath = uploadHeaderPicResultEntity.getData();

            // 6.存入ProjectVO对象中
            projectVO.setHeaderPicturePath(headerPicturePath);

        }else {

            // 7.上传失败则返回上传页面，返回错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);

            return "project-launch";
        }



        // 二、详情图片上传

        // 1.创建一个用来存放详情图片路径的集合
        List<String> detailPicturePathList = new ArrayList<String>();

        // 2.检查 detailPictureList 是否有效
        if (detailPictureList == null || detailPictureList.size() == 0) {

            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);

            return "project-launch";
        }

        // 3.遍历 detailPictureList 集合
        for (MultipartFile detailPicture : detailPictureList) {

            // 4.判断是否为空
            if (detailPicture.isEmpty()) {

                // 5.检测到详情图片中单个文件为空也是回去显示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);

                return "project-launch";
            }

            // 6.执行上传
            ResultEntity<String> detailUploadResultEntity = CrowdfundingUtil.uploadFileToOss(ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    detailPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    Objects.requireNonNull(detailPicture.getOriginalFilename()));

            // 7.检查上传结果
            String detailUploadResult = detailUploadResultEntity.getResult();
            if (ResultEntity.SUCCESS.equals(detailUploadResult)) {

                String detailPicturePath = detailUploadResultEntity.getData();

                // 8.收集刚刚上传的图片的访问路径
                detailPicturePathList.add(detailPicturePath);
            }else {

                // 9.如果上传失败则返回到表单页面并显示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);

                return "project-launch";
            }

        }
        // 10.将存放了详情图片访问路径的集合存入 ProjectVO 中
        projectVO.setDetailPicturePathList(detailPicturePathList);

        // 三、 后续操作
        // 1.将 ProjectVO 对象存入 Session 域
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
        // 2.以完整的访问路径前往下一个收集回报信息的页面
        return "redirect:http://localhost/project/return/info/page";
    }
}
