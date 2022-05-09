package com.hypocriticalfish.crowdfunding.mapper;

import com.hypocriticalfish.crowdfunding.entity.Admin;
import com.hypocriticalfish.crowdfunding.entity.AdminExample;
import com.hypocriticalfish.crowdfunding.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    List<Admin> selectAdminByKeyWord(String keyword);

    void deleteOldRelationship(String adminId);

    void insertNewRelationship(@Param("adminId") String adminId, @Param("roleIdList") List<Integer> roleIdList);
}