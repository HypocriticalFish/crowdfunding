package com.hypocriticalfish.crowdfunding.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/9 19:07
 * @Description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalTypeVO {

    private Integer id;

    private String name;

    private String remark;

    private List<PortalProjectVO> portalProjectVOList;

}
