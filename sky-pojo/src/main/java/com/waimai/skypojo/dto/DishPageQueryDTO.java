package com.waimai.skypojo.dto;

import lombok.Data;

@Data
public class DishPageQueryDTO {

    private int page;

    private int pageSize;

    //菜品名称
    private String name;

    //分类id
    private Long categoryId;

    //状态 0 禁用  1 启用
    private Integer status;
}
