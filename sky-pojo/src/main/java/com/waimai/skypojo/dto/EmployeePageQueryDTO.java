package com.waimai.skypojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
//@ApiModel("员工分页查询参数")
public class EmployeePageQueryDTO implements Serializable {

    //员工姓名
    private String name;

    //当前页码
    private Integer pageNum;

    //每页显示记录数
    private Integer pageSize;
}
