package com.hypocriticalfish.crowdfunding.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/8 18:15
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {

    private String loginacct;

    private String userpswd;

    private String username;

    private String email;

    private String phoneNum;

    private String code;
}
