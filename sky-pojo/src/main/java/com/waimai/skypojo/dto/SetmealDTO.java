package com.waimai.skypojo.dto;

import com.waimai.skypojo.entity.SetmealDish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDTO implements Serializable {
    //套餐id
    private Long id;

    //分类id
    private Long categoryId;

    //套餐名称
    private String name;

    //套餐价格
    private BigDecimal price;

    //状态 0 停售 1 起售
    private Integer status;

    //描述信息
    private String description;

    //图片
    private String image;

    //套餐内详情
    private List<SetmealDish> setmealDishes=new ArrayList<>();
}
