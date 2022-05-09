package com.hypocriticalfish.crowdfunding.handler;

import com.hypocriticalfish.crowdfunding.api.MySQLRemoteService;
import com.hypocriticalfish.crowdfunding.api.RedisRemoteService;
import com.hypocriticalfish.crowdfunding.config.ShortMessageProperties;
import com.hypocriticalfish.crowdfunding.constant.CrowdConstant;
import com.hypocriticalfish.crowdfunding.entity.po.MemberPO;
import com.hypocriticalfish.crowdfunding.entity.vo.MemberLoginVO;
import com.hypocriticalfish.crowdfunding.entity.vo.MemberVO;
import com.hypocriticalfish.crowdfunding.util.CrowdfundingUtil;
import com.hypocriticalfish.crowdfunding.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/8 16:22
 * @Description
 */
@Controller
public class MemberHandler {

    @Autowired
    private ShortMessageProperties shortMessageProperties;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/auth/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:http://localhost/";
    }

    @RequestMapping("/auth/member/do/login")
    public String login(
            @RequestParam("loginacct")String loginacct,
            @RequestParam("userpswd")String userpswd,
            ModelMap modelMap,
            HttpSession session){
        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "member-login";
        }
        MemberPO memberPO = resultEntity.getData();
        if (memberPO == null) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 2.比较密码
        String dbPswd = memberPO.getUserpswd();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matchesResult = passwordEncoder.matches(userpswd, dbPswd);
        if (!matchesResult) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);
        return "redirect:http://localhost/auth/member/to/center/page";
    }

    @RequestMapping("/auth/do/member/register")
    public String register(MemberVO memberVO, ModelMap modelMap){
        // 1.获取用户输入的手机号
        String phoneNum = memberVO.getPhoneNum();
        // 2.拼Redis中存储验证码的key
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        // 3.从Redis读取Key对应的验证码
        ResultEntity<String> resultEntity = redisRemoteService.getRedisStringValueByKey(key);
        // 4.检查查询操作是否有效
        String result = resultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "member-reg";
        }
        String redisCode = resultEntity.getData();
        if (redisCode == null) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }
        // 5.如果从Redis能够查询到验证码，则校验
        String formCode = memberVO.getCode();
        if(!Objects.equals(formCode, redisCode)){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }
        // 6.如果验证码一致则从Redis删除
        ResultEntity<String> removeResultEntity = redisRemoteService.removeRedisKeyRemote(key);
        // 7.执行密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String userpswd = memberVO.getUserpswd();
        String encodePswd = passwordEncoder.encode(userpswd);
        memberVO.setUserpswd(encodePswd);
        // 8.执行保存
        // 8.1创建空的MemberPO对象
        MemberPO memberPO = new MemberPO();
        // 8.2复制属性
        BeanUtils.copyProperties(memberVO, memberPO);
        // 8.3调用远程方法执行保存
        ResultEntity<String> saveMemberResultEntity = mySQLRemoteService.saveMember(memberPO);

        if (ResultEntity.FAILED.equals(saveMemberResultEntity.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveMemberResultEntity.getMessage());
            return "member-reg";
        }

        return "redirect:http://localhost/auth/member/to/login/page";
    }

    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum")String phoneNum){
        // 1.发送验证码到phoneNum手机
        //ResultEntity<String> sendMessageResultEntity = CrowdfundingUtil.sendShortMessage(phoneNum, shortMessageProperties.getAppcode(), shortMessageProperties.getTemplateId());
        ResultEntity<String> sendMessageResultEntity = CrowdfundingUtil.sendShortMessage(phoneNum, shortMessageProperties.getAppcode(), shortMessageProperties.getTemplateId());

        // 2. 判断短信发送结果
        if (ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())) {
            // 3.发送成功则存入redis
            String code = sendMessageResultEntity.getData();
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 5, TimeUnit.MINUTES);
            if (ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult())) {
                return ResultEntity.successWithoutData();
            }else {
                return saveCodeResultEntity;
            }
        }else {
            return sendMessageResultEntity;
        }
    }
}
