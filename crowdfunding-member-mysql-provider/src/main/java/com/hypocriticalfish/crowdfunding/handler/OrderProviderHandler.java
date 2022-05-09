package com.hypocriticalfish.crowdfunding.handler;

import com.hypocriticalfish.crowdfunding.entity.vo.AddressVO;
import com.hypocriticalfish.crowdfunding.entity.vo.OrderProjectVO;
import com.hypocriticalfish.crowdfunding.entity.vo.OrderVO;
import com.hypocriticalfish.crowdfunding.service.OrderService;
import com.hypocriticalfish.crowdfunding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Hypocritical Fish
 * @Create 2022/5/9 21:50
 * @Description
 */
@RestController
public class OrderProviderHandler {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/get/order/project/vo/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("projectId") Integer projectId, @RequestParam("returnId") Integer returnId) {

        try {
            OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(projectId, returnId);

            return ResultEntity.successWithData(orderProjectVO);
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }

    }

    @RequestMapping("/save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO) {

        try {
            orderService.saveOrderVO(orderVO);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/save/address/vo/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO) {

        try {
            orderService.saveAddressVO(addressVO);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/address/vo/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId) {

        try {
            List<AddressVO> addressVOList =  orderService.getAddressVOList(memberId);

            return ResultEntity.successWithData(addressVOList);
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }
}
