package com.rain.mapper;

import com.rain.entity.Cart;

public interface CartMapper {

    /**
     * 插入购物车数据表
     * @param cart
     * @return
     */
    Integer insert(Cart cart);

    /**
     * 根据用户的uid以及对应的pid,查询购物车是否存在这样的商品
     * @param uid 用户的uid
     * @param pid 用户加入购物车的商品pid
     * @return 购物车的某个商品
     */
    Cart findByUidAndPid(Integer uid,Integer pid);

    /**
     * 根据传过来的cart对象，根据cid进行字段内容的修改
     * @param cart 需要修改的cart对象
     * @return 受影响的行数
     */
    Integer updateCart(Cart cart);
}
