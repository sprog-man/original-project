package com.waimai.skyserver.service.impl;

import com.waimai.skycommon.context.BaseContext;
import com.waimai.skypojo.dto.ShoppingCartDTO;
import com.waimai.skypojo.entity.Dish;
import com.waimai.skypojo.entity.Setmeal;
import com.waimai.skypojo.entity.ShoppingCart;
import com.waimai.skyserver.mapper.DishMapper;
import com.waimai.skyserver.mapper.SetmealMapper;
import com.waimai.skyserver.mapper.ShoppingCartMapper;
import com.waimai.skyserver.service.SetmealService;
import com.waimai.skyserver.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     */
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //判断当前加入的购物车数据是否在购物车中
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        //如果已经存在了，只需要将数量加1
        if (list != null && list.size() > 0) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1); //然后进行update更新数据
            shoppingCartMapper.updateNumberById(cart);
        }else {
            //如果不存在，则插入一条购物车数据

            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null) {
                //本次添加到购物车的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());

            }else {
                //本次添加到购物车是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal =setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());

            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }


    }

    /**
     * 查看购物车数据
     *
     * @return
     */
    public List<ShoppingCart> showShoppingCart() {
        //获取当前微信用户的 id
        Long userId = BaseContext.getCurrentId();
        //创建查询条件,因为list方法需要的是ShoppingCart对象
        ShoppingCart shoppingCart =ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        return list;
    }

    /**
     * 一键清空购物车
     */
    public void cleanShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);

    }

    /**
     * 删除购物车中的一个商品
     *
     * @param shoppingCartDTO
     */
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list != null && list.size() > 0) {
            shoppingCart = list.get(0);
            Integer number = shoppingCart.getNumber();
            if (number >1) {
                shoppingCart.setNumber(number - 1);
                shoppingCartMapper.updateNumberById(shoppingCart);
            }else {
                shoppingCartMapper.deleteById(shoppingCart.getId());
            }
        }
    }

}
