package com.hypocriticalfish.crowdfunding.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/9 22:05
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String receiveName;

    private String phoneNum;

    private String address;

    private Integer memberId;

}
