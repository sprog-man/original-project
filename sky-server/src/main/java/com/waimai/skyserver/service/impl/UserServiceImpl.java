package com.waimai.skyserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waimai.skycommon.constant.MessageConstant;
import com.waimai.skycommon.exception.LoginFailedException;
import com.waimai.skycommon.properties.WechatProperties;
import com.waimai.skycommon.result.Result;
import com.waimai.skycommon.utils.HttpClientUtil;
import com.waimai.skypojo.dto.UserLoginDTO;
import com.waimai.skypojo.entity.User;
import com.waimai.skypojo.vo.UserLoginVO;
import com.waimai.skyserver.mapper.UserMapper;
import com.waimai.skyserver.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WechatProperties wechatProperties;

    @Autowired
    private UserMapper userMapper;

    // 微信登录接口
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    public User wxlogin(UserLoginDTO userLoginDTO) {
        String openid=getOpenid(userLoginDTO.getCode());

        //判断openid是否为空，如果为空表示登录失败，抛出业务异常
        if(openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //判断当前用户是否为新对象
        User user = userMapper.getByOpenid(openid);

        //如果是新用户，自动完成注册
        if(user==null){
            user=User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();

            userMapper.insert(user);
        }


        //返回这个用户对象
        return user;
    }

    /**
     * 调用微信接口服务，获得当前微信用户的openid
     * @param code
     * @return
     */

    public String getOpenid(String code) {
        //调用微信接口服务，获得当前微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid", wechatProperties.getAppId());
        map.put("secret", wechatProperties.getAppSecret());
        map.put("js_code", code);
        //授权类型的值是固定的，直接填写授权类型
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN_URL, map);

        //将json序列化为对象Map<string object>
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
     }
}
