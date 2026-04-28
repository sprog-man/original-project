package com.waimai.skyserver.controller.admin;

import com.waimai.skycommon.result.PageResult;
import com.waimai.skycommon.result.Result;
import com.waimai.skypojo.dto.CategoryDTO;
import com.waimai.skypojo.dto.CategoryPageQueryDTO;
import com.waimai.skypojo.entity.Category;
import com.waimai.skyserver.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */

@RestController("adminCategoryController")
@RequestMapping("/admin/category")
@Slf4j
@Tag(name = "管理员菜品分类管理",description = "菜品分类管理")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param categoryDTO
     * @return
     */

    @Operation(summary = "新增分类")
    @PostMapping
    public Result<String> save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "分类分页查询")
    public Result<PageResult<Category>> page(@ParameterObject CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分页查询：{}", categoryPageQueryDTO);
        PageResult<Category> pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据id删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @Operation(summary = "根据id删除分类")
    public Result<String> deleteById(Long id){
        log.info("删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改分类
     *
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @Operation(summary = "修改分类")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类：{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }


    /**
     * 启用禁用分类
     *
     * @param status
     * @param id
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启用禁用分类")
    public Result<String> startOrStop(@PathVariable Integer status, Long id){
        log.info("启用禁用分类：{}", id);
        categoryService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @param type
     */
    @GetMapping("/list")
    @Operation(summary = "根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        log.info("根据类型查询分类：{}", type);
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }


}
