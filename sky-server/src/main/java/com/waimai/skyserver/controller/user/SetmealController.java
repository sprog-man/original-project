package com.waimai.skyserver.controller.user;

import com.waimai.skycommon.constant.StatusConstant;
import com.waimai.skycommon.result.Result;
import com.waimai.skypojo.entity.Setmeal;
import com.waimai.skypojo.vo.DishItemVO;
import com.waimai.skypojo.vo.DishVO;
import com.waimai.skyserver.mapper.SetmealMapper;
import com.waimai.skyserver.service.SetmealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Tag(name = "用户查询套餐模块")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据分类id查询套餐
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "查询套餐")
    @Cacheable(cacheNames = "setmealCache", key = "#categoryId") //key: setmealCache::categoryId
    public Result<List<Setmeal>> list(Long categoryId) {
        Setmeal setmeal = Setmeal.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        log.info("根据分类id查询套餐：{}", categoryId);
        List<Setmeal> list = setmealService.list(setmeal);
        log.info("查询结果数量：{}, 数据：{}", list.size(), list);
        return Result.success(list);
    }

    /**
     * 根据套餐id查询包含的菜品列表
     *
     * @param id
     * @return
     */
    @GetMapping("/dish/{id}")
    @Operation(summary = "查询套餐包含的菜品列表")
    public Result<List<DishItemVO>> dishList(@PathVariable Long id) {
        log.info("查询套餐包含的菜品列表：{}", id);
        List<DishItemVO> list = setmealService.getSetmealDishItemById(id);
        return Result.success(list);
    }
}
