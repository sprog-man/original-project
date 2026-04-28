package com.waimai.skyserver.service;

import com.waimai.skycommon.result.PageResult;
import com.waimai.skypojo.dto.CategoryDTO;
import com.waimai.skypojo.dto.CategoryPageQueryDTO;
import com.waimai.skypojo.entity.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 新增分类
     *
     * @param categoryDTO
     */

    void save(CategoryDTO categoryDTO);

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 删除分类
     * @param id
     */
    void deleteById(Long id);

    /**
     * 修改分类
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 启用禁用分类
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 查询分类
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
