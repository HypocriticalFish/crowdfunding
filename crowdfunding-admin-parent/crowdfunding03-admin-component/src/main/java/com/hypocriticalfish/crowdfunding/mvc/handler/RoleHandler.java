package com.hypocriticalfish.crowdfunding.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.hypocriticalfish.crowdfunding.entity.Role;
import com.hypocriticalfish.crowdfunding.service.RoleService;
import com.hypocriticalfish.crowdfunding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/3 17:26
 * @Description
 */
@RestController
public class RoleHandler {
    @Autowired
    private RoleService roleService;


    @PreAuthorize("hasRole('部长')")
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);

        //若上面的操作抛出异常，则交给异常映射机制处理
        return ResultEntity.successWithData(pageInfo);
    }


    @RequestMapping("/role/save.json")
    public ResultEntity<String> saveRole(@RequestParam("roleName") String roleName) {

        System.out.println(roleName);
        roleService.saveRole(new Role(null, roleName));

        return ResultEntity.successWithoutData();
    }


    @RequestMapping("/role/update.json")
    public ResultEntity<String> updateRole(Role role) {

        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }


    @RequestMapping("/role/remove/by/role/id/array.json")
    public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdList) {

        roleService.removeRole(roleIdList);

        return ResultEntity.successWithoutData();
    }

}
