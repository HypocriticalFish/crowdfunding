package com.hypocriticalfish.crowdfunding.mapper;

import com.hypocriticalfish.crowdfunding.entity.po.ProjectPO;
import com.hypocriticalfish.crowdfunding.entity.po.ProjectPOExample;
import com.hypocriticalfish.crowdfunding.entity.vo.DetailProjectVO;
import com.hypocriticalfish.crowdfunding.entity.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectPOMapper {
    int countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    void insertTypeRelationShip(@Param("typeIdList") List<Integer> typeIdList, @Param("projectId") Integer projectId);

    void insertTagRelationShip(@Param("tagIdList") List<Integer> tagIdList, @Param("projectId") Integer projectId);

    List<PortalTypeVO> selectProtalTypeVOList();

    DetailProjectVO selectDetailProjectVO(Integer projectId);
}