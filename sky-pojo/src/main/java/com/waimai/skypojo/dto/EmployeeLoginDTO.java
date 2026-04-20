package com.waimai.skypojo.dto;

import lombok.Data;


// @ApiModel(description = "员工登录时传递的数据模型")
@Data
public class EmployeeLoginDTO {

    //@ApiModelProperty(value = "用户名",required = true)
    private String username;

    //@ApiModelProperty(value = "密码",required = true)
    private String password;
}
