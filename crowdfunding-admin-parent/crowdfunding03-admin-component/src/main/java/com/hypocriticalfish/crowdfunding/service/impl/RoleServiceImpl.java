package com.hypocriticalfish.crowdfunding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hypocriticalfish.crowdfunding.entity.Role;
import com.hypocriticalfish.crowdfunding.entity.RoleExample;
import com.hypocriticalfish.crowdfunding.mapper.RoleMapper;
import com.hypocriticalfish.crowdfunding.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/3 17:24
 * @Description
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void removeRole(List<Integer> roleIdList) {
        RoleExample example = new RoleExample();

        RoleExample.Criteria criteria = example.createCriteria();

        criteria.andIdIn(roleIdList);

        roleMapper.deleteByExample(example);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);

        List<Role> roleList = roleMapper.selectRoleByKeyWord(keyword);

        return new PageInfo<>(roleList);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insertSelective(role);
    }

}
