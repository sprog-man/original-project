package com.waimai.skyserver.service;

import com.waimai.skycommon.result.PageResult;
import com.waimai.skypojo.dto.SetmealDTO;
import com.waimai.skypojo.dto.SetmealPageQueryDTO;
import com.waimai.skypojo.entity.Setmeal;
import com.waimai.skypojo.vo.DishItemVO;
import com.waimai.skypojo.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);


    /**
     * 分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);


    /**
     * 批量删除套餐
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);


    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    SetmealVO getById(Long id);


    /**
     * 修改套餐
     *
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 批量起售和停售套餐
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);



    /**
     * 条件查询套餐
     *
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getSetmealDishItemById(Long id);



}
