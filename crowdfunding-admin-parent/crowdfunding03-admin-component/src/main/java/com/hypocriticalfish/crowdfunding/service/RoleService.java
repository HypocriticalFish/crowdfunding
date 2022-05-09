package com.hypocriticalfish.crowdfunding.service;

import com.github.pagehelper.PageInfo;
import com.hypocriticalfish.crowdfunding.entity.Role;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/3 17:24
 * @Description
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleIdList);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);
}
