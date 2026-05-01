package com.waimai.skyserver.mapper;

import com.waimai.skypojo.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    /**
     * 批量插入订单详情
     * @param orderDetailList
     * 配置xml
     */
    void insertBatch(List<OrderDetail> orderDetailList);
}
