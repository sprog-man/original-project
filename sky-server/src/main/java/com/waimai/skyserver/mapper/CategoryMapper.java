package com.waimai.skyserver.mapper;


import com.github.pagehelper.Page;
import com.waimai.skycommon.enumeration.OperationType;
import com.waimai.skypojo.dto.CategoryPageQueryDTO;
import com.waimai.skypojo.entity.Category;
import com.waimai.skyserver.annotation.AutoFill;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 插入菜品分类数据
     * @param category
     */
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)" +
            " VALUES" +
            " (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     * 使用xml配置分页查询
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据id删除菜品分类数据
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据id修改菜品分类数据
     * @param category
     * 配置xml
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    /**
     * 根据类型查询菜品分类数据
     * @param type
     * @return
     * 配置xml
     */
    List<Category> list(Integer type);
}
