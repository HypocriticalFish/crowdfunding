package com.hypocriticalfish.crowdfunding.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/9 19:08
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalProjectVO {
    private Integer projectId;
    private String projectName;
    private String headerPicPath;
    private Integer money;
    private String deployDate;
    private Integer percentage;
    private Integer supporter;
}
