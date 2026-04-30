package com.waimai.skyserver.controller.admin;

import com.github.pagehelper.Page;
import com.waimai.skycommon.result.PageResult;
import com.waimai.skycommon.result.Result;
import com.waimai.skypojo.dto.DishDTO;
import com.waimai.skypojo.dto.DishPageQueryDTO;
import com.waimai.skypojo.entity.Dish;
import com.waimai.skypojo.vo.DishVO;
import com.waimai.skyserver.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Tag(name = "管理员菜品管理",description = "菜品管理")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品和对应口味
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "新增菜品")
    public Result<String> save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        //清理缓存数据
        //只需要精确清理某个分类下的缓存数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache( key);
        return Result.success();
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "菜品分页查询")
    public Result<PageResult<Dish>> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询：{}", dishPageQueryDTO);
        PageResult<Dish> page = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(page);
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @Operation(summary = "批量删除菜品")
    // @RequestParam注解可以让mvc框架将请求参数绑定到方法参数上，让mvc帮我们把请求参数分隔成为方法参数
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量删除菜品：{}", ids);
        dishService.deleteBatch(ids);

        //删除可能涉及多个菜品分类的缓存数据
        //一步到位，将所有的菜品缓存数据清理掉，所有以dish_ 开头的key
        //delete支持传入collection集合，删除的时候不支持通配符操作，所以传入集合
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 根据id查询菜品和对应的口味
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根据id查询菜品：{}", id);
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     *
     * @param dishDTO
     * @return
     */
    @PutMapping
    @Operation(summary = "修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        //简化操作，直接删除所有菜品的缓存数据
        cleanCache( "dish_*");
        return Result.success();
    }


    /**
     * 清楚缓存数据
     *
     * @param pattern
     */
    public void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
