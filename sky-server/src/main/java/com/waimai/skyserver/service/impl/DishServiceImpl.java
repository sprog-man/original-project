package com.waimai.skyserver.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.waimai.skycommon.constant.MessageConstant;
import com.waimai.skycommon.constant.StatusConstant;
import com.waimai.skycommon.exception.DeletionNotAllowedException;
import com.waimai.skycommon.result.PageResult;
import com.waimai.skypojo.dto.DishDTO;
import com.waimai.skypojo.dto.DishPageQueryDTO;
import com.waimai.skypojo.entity.Dish;
import com.waimai.skypojo.entity.DishFlavor;
import com.waimai.skypojo.vo.DishVO;
import com.waimai.skyserver.mapper.DishFlavorMapper;
import com.waimai.skyserver.mapper.DishMapper;
import com.waimai.skyserver.mapper.SetmealDishMapper;
import com.waimai.skyserver.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增菜品和对应口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO){
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);


        //向菜品表插入1条数据
        dishMapper.insert(dish);

        //获取insert语句生成的主键值,即Dish对象中的id值
        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            //向口味表插入n条数据
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */

    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        //开始分页
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);


        //封装分页结果
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //1.判断菜品是否能够删除--是否存在起售中的菜品？？
        for (Long id : ids){
          Dish dish =  dishMapper.getById(id);
          if (dish.getStatus() == StatusConstant.ENABLE){
              //当前菜品处于起售中，不能删除
              throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
          }
        }
        //2.判断当前菜品是否能够删除--当前菜品是否被套餐关联？？
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && setmealIds.size() > 0){
            //当前菜品被套餐关联，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        /*//3.删除菜品表中的菜品数据
        for (Long id : ids){
            dishMapper.deleteById(id);
            //4.删除菜品关联的口味数据
            dishFlavorMapper.deleteByDishId(id);
        }*  代码优化，这样会导致多次循环遍历执行多条sql语句，效率低*/

        //3.根据ids批量删除菜品数据
        dishMapper.deleteByIds(ids);

        //4.根据ids批量删除菜品关联的口味数据
        dishFlavorMapper.deleteByDishIds(ids);
    }

    /**
     * 根据id查询菜品和对应的口味
     *
     * @param id
     * @return
     */

    public DishVO getById(Long id) {
        //1.根据id查询菜品数据
        Dish dish = dishMapper.getById(id);
        //2.根据菜品id查询口味数据
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        //3.将2个数据进行封装，封装到DishVO对象中并返回
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 修改菜品
     *
     * @param dishDTO
     */
    public void updateWithFlavor(DishDTO dishDTO) {
        //1.获取修改菜品表的数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        //2.删除原来的口味数据
        dishFlavorMapper.deleteByDishId(dishDTO.getId());

        //3.获取修改的口味数据
        Long dishId = dishDTO.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            //向口味表插入n条数据
            dishFlavorMapper.insertBatch(flavors);
        }



    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }

    /**
     * 根据条件查询菜品和口味
     *
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList=new ArrayList<>();
        for (Dish d : dishList){
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d, dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }
        return dishVOList;
    }
}
