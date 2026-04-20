package com.waimai.skypojo.dto;

import lombok.Data;

@Data
// @ApiModel(description = "新增员工时所需传入的数据请求")
public class EmployeeDTO {

    private long id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;
}
