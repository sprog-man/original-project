package com.waimai.skyserver.service;

import com.waimai.skypojo.dto.OrdersPaymentDTO;
import com.waimai.skypojo.dto.OrdersSubmitDTO;
import com.waimai.skypojo.entity.Orders;
import com.waimai.skypojo.vo.OrderPaymentVO;
import com.waimai.skypojo.vo.OrderSubmitVO;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO);

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);


    /**
     * 客户催单
     *
     * @param id
     */
    void reminder(Long id);
}
