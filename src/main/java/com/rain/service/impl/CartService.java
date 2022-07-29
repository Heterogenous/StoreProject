package com.rain.service.impl;

import com.rain.entity.Cart;
import com.rain.entity.User;
import com.rain.mapper.CartMapper;
import com.rain.mapper.UserMapper;
import com.rain.service.ICartService;
import com.rain.service.ex.CartNotFoundProductException;
import com.rain.service.ex.InsertException;
import com.rain.service.ex.UpdateException;
import com.rain.service.ex.UserNotFoundException;
import com.rain.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Cart getByUidAndPid(Integer uid, Integer pid) {
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        return result;
    }

    @Override
    public void addProductToCart(Integer uid,String username,Cart cart) {
        //根据uid查询用户是否存在
        User user = userMapper.findByUid(uid);
        if(user == null){
            throw new UserNotFoundException("添加至购物车:查询不到用户",Code.SELECT_FAIL);
        }
        //设置创建对象，时间，修改对象，修改时间
        cart.setCreatedUser(username);
        cart.setModifiedUser(username);
        Date date = new Date();
        cart.setCreatedTime(date);
        cart.setModifiedTime(date);
        Integer rows = cartMapper.insert(cart);
        if(rows != 1){
            throw new InsertException("商品添加到购物车异常!",Code.UPDATE_ERROR);
        }
    }

    @Override
    public void updateCart(Integer uid,String username,Cart cart) {
        //根据uid查询用户是否存在
        User user = userMapper.findByUid(uid);
        if(user == null){
            throw new UserNotFoundException("更新购物车中的商品:查询不到用户",Code.SELECT_FAIL);
        }
        //根据uid和pid查询，原购物车里的信息
        Cart result = cartMapper.findByUidAndPid(cart.getUid(), cart.getPid());
        if(result == null){
            throw new CartNotFoundProductException("在更新购物车商品中找不到该商品!",Code.SELECT_FAIL);
        }
        //以及数量
        cart.setNum(result.getNum()+cart.getNum());

        //设置修改者与时间
        cart.setModifiedUser(username);
        cart.setModifiedTime(new Date());
        Integer rows = cartMapper.updateCart(cart);
        if(rows!=1){
            throw new UpdateException("购物车中的商品更新异常!",Code.UPDATE_ERROR);
        }
    }
}
