package com.waimai.skypojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVO {

    private Long id; //用户id

    private String openid; //微信openid

    private String token; //用户jwt令牌
}
