package com.hypocriticalfish.crowdfunding.service;

import com.hypocriticalfish.crowdfunding.entity.po.MemberPO;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/7 22:15
 * @Description
 */
public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginacct);

    void saveMember(MemberPO memberPO);
}
