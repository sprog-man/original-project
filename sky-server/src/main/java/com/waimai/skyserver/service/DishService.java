package com.waimai.skyserver.service;

import com.github.pagehelper.Page;
import com.waimai.skycommon.result.PageResult;
import com.waimai.skypojo.dto.DishDTO;
import com.waimai.skypojo.dto.DishPageQueryDTO;
import com.waimai.skypojo.entity.Dish;
import com.waimai.skypojo.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     *  新增菜品和对应口味
     *  @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询菜品和对应的口味数据
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 修改菜品
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     * 条件查询菜品和口味
     *
     * @param dish
     * @return
     * 配置xml
     */
    List<DishVO> listWithFlavor(Dish dish);

}
