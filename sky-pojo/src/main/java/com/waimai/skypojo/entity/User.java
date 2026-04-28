package com.waimai.skypojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private static final long serialVersionUID = 1L;

    private Long id;

    //微信用户唯一标识
    private String openid;

    //微信昵称
    private String name;

    //手机号
    private String phone;

    //性别 1男性，2女性，0未知
    private String sex;

    //
    private String idNumber;

    //头像
    private String avatar;

    //注册时间
    private LocalDateTime createTime;
}
