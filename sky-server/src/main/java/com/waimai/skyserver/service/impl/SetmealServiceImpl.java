package com.waimai.skyserver.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.waimai.skycommon.constant.MessageConstant;
import com.waimai.skycommon.constant.StatusConstant;
import com.waimai.skycommon.exception.DeletionNotAllowedException;
import com.waimai.skycommon.result.PageResult;
import com.waimai.skypojo.dto.SetmealDTO;
import com.waimai.skypojo.dto.SetmealPageQueryDTO;
import com.waimai.skypojo.entity.Dish;
import com.waimai.skypojo.entity.Setmeal;
import com.waimai.skypojo.entity.SetmealDish;
import com.waimai.skypojo.vo.DishItemVO;
import com.waimai.skypojo.vo.SetmealVO;
import com.waimai.skyserver.mapper.DishMapper;
import com.waimai.skyserver.mapper.SetmealDishMapper;
import com.waimai.skyserver.mapper.SetmealMapper;
import com.waimai.skyserver.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //向套餐表插入数据
        setmealMapper.insert(setmeal);

        //获取生成的套餐id
        Long setmealId = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish ->  {
            setmealDish.setSetmealId(setmealId);
        });

        //保存套餐和菜品的关联关系
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {
        ids.forEach(id -> {
            Setmeal setmeal= setmealMapper.getById(id);
            if (setmeal.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }

            //1.删除套餐和菜品的关联关系
            setmealDishMapper.deleteBySetmealId(id);

            //2.删除套餐数据
            setmealMapper.delete(id);
        });

    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */

    public SetmealVO getById(Long id) {
        List<SetmealDish> dishBySetmealId = setmealDishMapper.getDishBySetmealId(id);
        SetmealVO setmealVO = new SetmealVO();
        Setmeal setmeal = setmealMapper.getById(id);
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(dishBySetmealId);
        return setmealVO;
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO
     */
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //更新套餐
        setmealMapper.update(setmeal);

        //删除之前的套餐和菜品的关联关系
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());

        //批量保存套餐和菜品的关联关系
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealDTO.getId());
        });
        setmealDishMapper.insertBatch(setmealDishes);

    }

    /**
     * 批量起售、批量停售,即上架或下架套餐
     *
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        //有停售菜品提示"套餐内包含未起售菜品，无法起售"
        if (status == StatusConstant.DISABLE){
            List<Dish> dishList=dishMapper.getBySetmealId(id);
            if (dishList != null && dishList.size() > 0){
                dishList.forEach(dish -> {
                    if (dish.getStatus() == StatusConstant.DISABLE){
                        throw new RuntimeException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });
            }
        }

    }


    /**
     * 条件查询
     * 根据分类id查询套餐
     *
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal){
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getSetmealDishItemById(Long id){
        return setmealMapper.getSetmealDishItemById(id);
    }


}
