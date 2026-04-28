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

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId){
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE); // 查询起售中的菜品
        log.info("查询菜品：{}", categoryId);
        List<DishVO> list = dishService.listWithFlavor(dish);
        return Result.success(list);
    }
}
