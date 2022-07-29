package com.rain.service;

import com.rain.entity.Cart;
import com.rain.entity.CartDTO;

import java.util.List;

public interface ICartService {
    /**
     * 根据用户的uid和商品的pid查询购物车里的商品
     * @param uid 用户uid
     * @param pid 商品pid
     */
    Cart getByUidAndPid(Integer uid, Integer pid);

    /**
     * 将cart对象存入到购物车表中
     * @param uid 修改用户的uid
     * @param username 修改用户的username
     * @param cart 即将被添加到表中的数据
     */
    void addProductToCart(Integer uid,String username,Cart cart);

    /**
     * 更新购物车中商品的信息
     * @param uid 修改者的uid
     * @param username 修改者的username
     * @param cart 购物车中商品的信息
     */
    void updateCart(Integer uid,String username,Cart cart);

    /**
     * 根据用户的uid获取所有购物车的商品,联合表查询 cart + product.image + product.title
     * @param uid 用户的uid
     * @return 购物车里的商品列表
     */
    List<CartDTO> getByUid(Integer uid);


    /**
     * 根据cid删除购物车里对应的商品
     * @param listCid
     */
    void batchDelete(List<Integer> listCid);
}
