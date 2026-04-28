package com.waimai.skyserver.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.waimai.skycommon.constant.MessageConstant;
import com.waimai.skycommon.constant.StatusConstant;
import com.waimai.skycommon.result.PageResult;
import com.waimai.skypojo.dto.CategoryDTO;
import com.waimai.skypojo.dto.CategoryPageQueryDTO;
import com.waimai.skypojo.entity.Category;
import com.waimai.skyserver.mapper.CategoryMapper;
import com.waimai.skyserver.mapper.DishMapper;
import com.waimai.skyserver.mapper.SetmealMapper;
import com.waimai.skyserver.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

/**
 * 分类业务层
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 新增分类
     *
     * @param categoryDTO
     *
     */
    public void save(CategoryDTO categoryDTO) {
        Category category=new Category();

        //属性拷贝
        BeanUtils.copyProperties(categoryDTO,category);

        //分类状态默认为禁用状态0
        category.setStatus(StatusConstant.DISABLE);

        // 设置创建时间、修改时间、创建人、修改人
        // category.setCreateTime(LocalDateTime.now());
        // category.setUpdateTime(LocalDateTime.now());
        // category.setCreateUser(BaseContext.getCurrentId());
        // category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.insert(category);

    }

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */

    public PageResult<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        //开启分页查询
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        long total = page.getTotal();
        List<Category> categories = page.getResult();
        return new PageResult<>(total,categories);

    }

    /**
     * 根据id删除分类
     *
     * @param id
     *
     */

    public void deleteById(Long id) {
        //查询当前分类是否关联了菜品，如果关联了就抛出业务异常
        Integer count = dishMapper.countByCategoryId(id);
        if(count > 0){
            //当前分类关联了菜品，抛出一个业务异常
            throw new RuntimeException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        // 查询当前分类是否关联了套餐，如果关联了就抛出业务异常
        count = setmealMapper.countByCategoryId(id);
        if(count > 0){
            //当前分类关联了套餐，抛出一个业务异常
            throw new RuntimeException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        //删除分类数据
        categoryMapper.deleteById(id);

    }

    /**
     * 修改分类
     *
     * @param categoryDTO
     *
     */

    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        // 设置修改时间、修改人
        // category.setUpdateTime(LocalDateTime.now());
        // category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    /**
     * 启用禁用分类
     *
     * @param status
     * @param id
     *
     */
    public void startOrStop(Integer status, Long id) {
        /*Category category = new Category();
        category.setStatus(status);
        category.setId(id);*/
        Category category=Category.builder()
                .status(status)
                .id(id)
                // .updateTime(LocalDateTime.now())
                // .updateUser(BaseContext.getCurrentId())
                .build();

        categoryMapper.update(category);

    }

    /**
     * 根据类型查询
     * @param type
     */

    public List<Category> list(Integer type) {
        return categoryMapper.list(type);

    }
}
