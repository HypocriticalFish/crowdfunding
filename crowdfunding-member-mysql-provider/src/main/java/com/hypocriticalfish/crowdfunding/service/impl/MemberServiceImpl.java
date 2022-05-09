package com.hypocriticalfish.crowdfunding.service.impl;

import com.hypocriticalfish.crowdfunding.entity.po.MemberPO;
import com.hypocriticalfish.crowdfunding.entity.po.MemberPOExample;
import com.hypocriticalfish.crowdfunding.mapper.MemberPOMapper;
import com.hypocriticalfish.crowdfunding.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/7 22:15
 * @Description
 */
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberPOMapper memberPOMapper;


    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public void saveMember(MemberPO memberPO) {
            memberPOMapper.insertSelective(memberPO);
    }

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {

        MemberPOExample example = new MemberPOExample();

        MemberPOExample.Criteria criteria = example.createCriteria();

        criteria.andLoginacctEqualTo(loginacct);

        List<MemberPO> list = memberPOMapper.selectByExample(example);

        if (list == null || list.size() == 0) {
            return null;
        }

        return list.get(0);
    }
}
