package com.waimai.skyserver.mapper;


import com.github.pagehelper.Page;
import com.waimai.skycommon.enumeration.OperationType;
import com.waimai.skypojo.dto.DishPageQueryDTO;
import com.waimai.skypojo.entity.Dish;
import com.waimai.skypojo.vo.DishVO;
import com.waimai.skyserver.annotation.AutoFill;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    /**
     * 根据分类id查询菜品数量
     * @param id 分类id
     * @return 菜品数量
     */
    @Select("select count(id) from dish where category_id = #{id}")
    Integer countByCategoryId(Long id);

    /**
     * 新增菜品和对应口味
     * @param dish
     *配置xml
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish  dish);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     * 配置xml
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据主键id查询菜品和对应的口味
     *
     * @param id
     * @return
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * 根据id删除菜品数据
     *
     * @param id
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据多个id批量删除菜品
     *
     * @param ids
     * 配置xml
     */
    void deleteByIds(List<Long> ids);

    /**
     * 修改菜品
     *
     * @param dish
     * 配置xml
     */
    void update(Dish dish);



    /**
     * 根据分类id获取菜品(不是套餐)
     *
     * @param dish
     * @return
     * 配置xml
     */
    List<Dish> list(Dish dish);

    /**
     * 根据套餐id查询菜品
     *
     * @param setmealId
     * @return
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);
}
