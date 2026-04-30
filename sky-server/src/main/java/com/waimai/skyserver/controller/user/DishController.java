package com.waimai.skyserver.controller.user;


import com.waimai.skycommon.constant.StatusConstant;
import com.waimai.skycommon.result.Result;
import com.waimai.skypojo.entity.Dish;
import com.waimai.skypojo.vo.DishVO;
import com.waimai.skyserver.mapper.DishMapper;
import com.waimai.skyserver.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Tag(name = "用户查询菜品模块")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId){
        //构造redis中的key，规则：dish_分类id
        String key = "dish_" + categoryId;

        //查询redis中是否存在菜品数据,放入什么类型的数据就用什么类型的数据接收
        List<DishVO> list =(List<DishVO>) redisTemplate.opsForValue().get(key);
        if (list != null && list.size() > 0) {
            //存在，直接返回,无需查询数据库
            return Result.success(list);
        }



        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE); // 查询起售中的菜品
        log.info("查询菜品：{}", categoryId);
        //如果不存在，需要查询数据库，并放入redis
        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key, list);
        return Result.success(list);
    }
}
