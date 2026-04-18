package com.waimai.skypojo.dto;

import lombok.Data;

@Data
public class EmployeeLoginDTO {

    //@ApiModelProperty(value = "用户名",required = true)
    private String username;

    //@ApiModelProperty(value = "密码",required = true)
    private String password;
}
