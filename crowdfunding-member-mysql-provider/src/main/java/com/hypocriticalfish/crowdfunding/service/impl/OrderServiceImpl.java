package com.hypocriticalfish.crowdfunding.service.impl;

import com.hypocriticalfish.crowdfunding.entity.po.AddressPO;
import com.hypocriticalfish.crowdfunding.entity.po.AddressPOExample;
import com.hypocriticalfish.crowdfunding.entity.po.OrderPO;
import com.hypocriticalfish.crowdfunding.entity.po.OrderProjectPO;
import com.hypocriticalfish.crowdfunding.entity.vo.AddressVO;
import com.hypocriticalfish.crowdfunding.entity.vo.OrderProjectVO;
import com.hypocriticalfish.crowdfunding.entity.vo.OrderVO;
import com.hypocriticalfish.crowdfunding.mapper.AddressPOMapper;
import com.hypocriticalfish.crowdfunding.mapper.OrderPOMapper;
import com.hypocriticalfish.crowdfunding.mapper.OrderProjectPOMapper;
import com.hypocriticalfish.crowdfunding.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/9 21:51
 * @Description
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderPOMapper orderPOMapper;

    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private AddressPOMapper addressPOMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId) {

        return orderProjectPOMapper.selectOrderProjectVO(returnId);
    }

    @Override
    public List<AddressVO> getAddressVOList(Integer memberId) {

        AddressPOExample example = new AddressPOExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        List<AddressPO> addressPOList = addressPOMapper.selectByExample(example);

        List<AddressVO> addressVOList = new ArrayList<>();
        for (AddressPO addressPO : addressPOList) {

            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO, addressVO);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveOrderVO(OrderVO orderVO) {

        OrderPO orderPO = new OrderPO();

        BeanUtils.copyProperties(orderVO, orderPO);

        OrderProjectPO orderProjectPO = new OrderProjectPO();

        BeanUtils.copyProperties(orderVO.getOrderProjectVO(), orderProjectPO);

        orderPOMapper.insert(orderPO);

        // 保存OrderProjectPO需要orderPO的id作为外键
        Integer id = orderPO.getId();
        // 这里需要先修改mapper，
        orderProjectPO.setOrderId(id);

        orderProjectPOMapper.insert(orderProjectPO);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveAddressVO(AddressVO addressVO) {

        AddressPO addressPO = new AddressPO();

        BeanUtils.copyProperties(addressVO, addressPO);

        addressPOMapper.insert(addressPO);

    }
}
