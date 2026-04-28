package com.waimai.skypojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SetmealPageQueryDTO implements Serializable {

    private int page;

    private int pageSize;

    private String name;

    //分类id
    private Long categoryId;

    //状态 0 停售 1 起售
    private Integer status;

}
