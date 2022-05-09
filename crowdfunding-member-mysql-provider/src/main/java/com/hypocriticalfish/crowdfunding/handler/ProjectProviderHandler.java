package com.hypocriticalfish.crowdfunding.handler;

import com.hypocriticalfish.crowdfunding.entity.vo.DetailProjectVO;
import com.hypocriticalfish.crowdfunding.entity.vo.PortalTypeVO;
import com.hypocriticalfish.crowdfunding.entity.vo.ProjectVO;
import com.hypocriticalfish.crowdfunding.service.ProjectService;
import com.hypocriticalfish.crowdfunding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/9 14:03
 * @Description
 */
@RestController
public class ProjectProviderHandler {
    @Autowired
    private ProjectService projectService;

    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId) {

        try {
            DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(projectId);
            return ResultEntity.successWithData(detailProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote() {

        try {
            List<PortalTypeVO> portalTypeVOList = projectService.getPortalTypeVO();

            return ResultEntity.successWithData(portalTypeVOList);
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId){
        try {
            projectService.saveProject(projectVO, memberId);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }
}
