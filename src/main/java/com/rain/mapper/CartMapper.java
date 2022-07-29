package com.rain.mapper;

import com.rain.entity.Cart;

public interface CartMapper {

    /**
     * 插入购物车数据表
     * @param cart
     * @return
     */
    Integer insert(Cart cart);
}
