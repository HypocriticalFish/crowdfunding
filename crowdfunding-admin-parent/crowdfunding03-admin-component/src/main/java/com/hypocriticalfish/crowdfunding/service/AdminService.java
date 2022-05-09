package com.hypocriticalfish.crowdfunding.service;

import com.github.pagehelper.PageInfo;
import com.hypocriticalfish.crowdfunding.entity.Admin;
import com.hypocriticalfish.crowdfunding.entity.Role;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/4/26 17:45
 * @Description
 */
public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveAdminRoleRelationship(String adminId, List<Integer> roleIdList);

    // AdminService
    Admin getAdminByLoginAcct(String username);
}
