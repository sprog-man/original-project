package com.waimai.skypojo.dto;

import lombok.Data;

/**
 * 用户端登录
 */

@Data
public class UserLoginDTO {
    //微信登录凭证：授权码
    private String code;

}
