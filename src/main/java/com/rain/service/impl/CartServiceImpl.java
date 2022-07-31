package com.rain.service.impl;

import com.rain.entity.Cart;
import com.rain.entity.CartDTO;
import com.rain.entity.User;
import com.rain.mapper.CartMapper;
import com.rain.mapper.UserMapper;
import com.rain.service.ICartService;
import com.rain.service.ex.*;
import com.rain.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

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
        //System.out.println(cart.getUid()+"---"+cart.getPid());
        Cart result = cartMapper.findByUidAndPid(cart.getUid(), cart.getPid());
        if(result == null){
            throw new CartNotFoundProductException("在更新购物车商品中找不到该商品!",Code.SELECT_FAIL);
        }
        //以及数量
        //cart.setNum(result.getNum()+cart.getNum());
        cart.setCid(result.getCid());
        //设置修改者与时间
        cart.setModifiedUser(username);
        cart.setModifiedTime(new Date());
        Integer rows = cartMapper.updateCart(cart);
        if(rows!=1){
            throw new UpdateException("购物车中的商品更新异常!",Code.UPDATE_ERROR);
        }
    }

    @Override
    public List<CartDTO> getByUid(Integer uid){
        List<CartDTO> list = cartMapper.findByUidWithProduct(uid);
        if(list.size() == 0){
            throw new CartNotFoundProductException("购物车里暂时没有商品!",Code.SELECT_FAIL);
        }
        return list;
    }

    @Override
    @Transactional
    public void batchDelete(List<Integer> listCid) {
        if(listCid.size() == 0){
            throw new CartNotFoundProductException("删除的商品不能为空!",Code.DEL_FAIL);
        }
        //转换成List<Integer>集合
//        List<Integer> cids = new ArrayList<>();
//        for (int i = 0; i < listCid.length; i++) {
//            cids.add(listCid[i]);
//        }
        Integer rows = cartMapper.batchDelete(listCid);
        if(rows <= 0){
            throw new DeleteException("删除商品异常!",Code.DEL_ERROR);
        }
    }

    @Override
    public List<CartDTO> batchSelect(List<Integer> listCid) {
        if(listCid.size() == 0){
            throw new CartNotFoundProductException("结算的商品不能为空!",Code.SELECT_FAIL);
        }
        List<CartDTO> cartDTOList = cartMapper.batchSelect(listCid);
        if(cartDTOList.size() == 0 || cartDTOList == null){
            throw new CartNotFoundProductException("购物车里没有这样的商品!",Code.SELECT_FAIL);
        }
        return cartDTOList;
    }
}
