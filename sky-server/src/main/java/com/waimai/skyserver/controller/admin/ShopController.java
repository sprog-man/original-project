package com.waimai.skyserver.controller.admin;

import com.waimai.skycommon.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Tag(name = "店铺相关接口管理与用户通用")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置营业状态
     *
     * @param status
     * @return
     */

    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置营业状态：{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    /**
     * 管理端查询店铺状态
     *
     * @return
     */
    @GetMapping("/status")
    @Operation(summary = "查询店铺状态")
    public Result< Integer> getStatus(){
        Integer status = (Integer)redisTemplate.opsForValue().get(KEY);
        log.info("查询店铺状态：{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }

}
