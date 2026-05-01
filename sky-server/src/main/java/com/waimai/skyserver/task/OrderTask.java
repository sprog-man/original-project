package com.waimai.skyserver.task;

import com.waimai.skypojo.entity.Orders;
import com.waimai.skyserver.mapper.OrderMapper;
import com.waimai.skyserver.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类，定时处理订单状态
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单(每分钟执行一次)
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void processTimeoutOrder() {
        log.info("定时处理超时订单", LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().minusMinutes(15);

        //检查哪些订单超时的查询逻辑:select * from orders where status = 1 and order_time < (当前时间 减15分钟)?
        List<Orders> ordersList=orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, LocalDateTime.now().minusMinutes(15));

        //将超时订单的订单状态改为取消状态
        if (ordersList != null && ordersList.size() > 0){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时取消");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }

    /**
     * 处理订单过长时间处于派送中的订单(每天凌晨1点执行)
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder() {
        log.info("处理订单过长时间处于派送中的订单", LocalDateTime.now());

        //检查哪些订单处于派送中的查询逻辑:select * from orders where status = 4 and order_time < (当前时间 减1小时)减去1小时刚刚好就是前一天的订单?
        List<Orders> ordersList=orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().minusHours(1));

        if (ordersList != null && ordersList.size() > 0){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            }
        }

    }
}
