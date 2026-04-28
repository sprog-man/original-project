package com.waimai.skyserver.service;

import com.waimai.skycommon.result.Result;
import com.waimai.skypojo.dto.UserLoginDTO;
import com.waimai.skypojo.entity.User;
import com.waimai.skypojo.vo.UserLoginVO;

public interface UserService {
    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    User wxlogin(UserLoginDTO userLoginDTO);

}
