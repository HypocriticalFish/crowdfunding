package com.hypocriticalfish.crowdfunding.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/8 19:20
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginVO implements Serializable {

    private static final long serialVersionUID = 2124931623326079892L;

    private Integer id;

    private String username;

    private String email;
}
