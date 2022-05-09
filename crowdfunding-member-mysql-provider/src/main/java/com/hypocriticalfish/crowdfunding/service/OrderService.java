package com.hypocriticalfish.crowdfunding.service;

import com.hypocriticalfish.crowdfunding.entity.vo.AddressVO;
import com.hypocriticalfish.crowdfunding.entity.vo.OrderProjectVO;
import com.hypocriticalfish.crowdfunding.entity.vo.OrderVO;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/9 21:51
 * @Description
 */
public interface OrderService {
    OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

    List<AddressVO> getAddressVOList(Integer memberId);

    void saveAddressVO(AddressVO addressVO);

    void saveOrderVO(OrderVO orderVO);
}
