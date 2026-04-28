package com.waimai.skypojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 套餐与菜品关系
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    //套餐与菜品关系id
    private Long id;

    //套餐id
    private Long setmealId;

    //菜品id
    private Long dishId;

    //菜品名称 （冗余字段）
    private String name;

    //菜品原价 （冗余字段）
    private BigDecimal price;

    //份数
    private Integer copies;
}
