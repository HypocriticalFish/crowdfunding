package com.hypocriticalfish.crowdfunding.handler;

import com.hypocriticalfish.crowdfunding.api.MySQLRemoteService;
import com.hypocriticalfish.crowdfunding.constant.CrowdConstant;
import com.hypocriticalfish.crowdfunding.entity.vo.PortalTypeVO;
import com.hypocriticalfish.crowdfunding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/7 23:25
 * @Description
 */
@Controller
public class PortalHandler {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;


    @RequestMapping("/")
    public String showPortalPage(Model model) {

        // 调用MySQLService 提供的方法查询首页数据
        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectDataRemote();

        // 2.检查查询结果
        String result = resultEntity.getResult();
        if (ResultEntity.SUCCESS.equals(result)) {

            // 3.获取查询结果数据
            List<PortalTypeVO> list = resultEntity.getData();

            // 4.存入模型
            model.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA, list);
        }

        return "portal";
    }
}
