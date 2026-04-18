package com.waimai.skycommon.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {

    /**
     * 管理端员工生成jwt令牌
     */
    private String adminSecretKey; //管理员 token 签名密钥
    private long adminTtl; //管理员 token 过期时间(毫秒)
    private String adminTokenName; //管理员 token 在请求头中的名称

    /**
     * 用户端微信用户生成jwt令牌相关配置
     */
    private String userSecretKey; //用户 token 签名密钥
    private long userTtl; //用户 token 过期时间
    private String userTokenName;//用户 token 在请求头中的名称


}
