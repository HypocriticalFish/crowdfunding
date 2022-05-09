package com.hypocriticalfish.crowdfunding.service;

import com.hypocriticalfish.crowdfunding.entity.vo.DetailProjectVO;
import com.hypocriticalfish.crowdfunding.entity.vo.PortalTypeVO;
import com.hypocriticalfish.crowdfunding.entity.vo.ProjectVO;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/9 14:03
 * @Description
 */
public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);
    List<PortalTypeVO> getPortalTypeVO();
    DetailProjectVO getDetailProjectVO(Integer projectId);
}
