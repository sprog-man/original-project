package com.waimai.skypojo.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLoginVO {

    //@ApiModelProperty("主键值")
    private Long id;

    //@ApiModelProperty( "用户名")
    private String username;

    //@ApiModelProperty("姓名")
    private String name;

    //@ApiModelProperty("jwt令牌")
    private String token;
}
