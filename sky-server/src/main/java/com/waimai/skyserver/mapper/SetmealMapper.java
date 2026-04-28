package com.waimai.skyserver.mapper;


import com.github.pagehelper.Page;
import com.waimai.skycommon.enumeration.OperationType;
import com.waimai.skypojo.dto.SetmealPageQueryDTO;
import com.waimai.skypojo.entity.Setmeal;
import com.waimai.skypojo.vo.DishItemVO;
import com.waimai.skypojo.vo.SetmealVO;
import com.waimai.skyserver.annotation.AutoFill;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {
    /**
     * 新增套餐
     *
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal  setmeal);


    /**
     * 分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);


    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * 删除套餐数据
     */
    @Delete("delete  from setmeal where id=#{id}")
    void delete(Long id);


    /**
     * 修改套餐 数据
     *
     * @param setmeal
     * 配置xml
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);



    /**
     * 根据分类id查询套餐数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{id}")
    Integer countByCategoryId(Long id);

    /**
     * 条件动态查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getSetmealDishItemById(Long id);



}
