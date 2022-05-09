package com.hypocriticalfish.crowdfunding.service;

import com.hypocriticalfish.crowdfunding.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/3 23:15
 * @Description
 */
public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
