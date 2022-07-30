package com.rain.controller;

import com.rain.entity.Cart;
import com.rain.entity.CartDTO;
import com.rain.service.ICartService;
import com.rain.util.Code;
import com.rain.util.JsonResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.ServerSocket;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController extends BaseController{
    @Autowired
    private ICartService cartService;

    @RequestMapping("/get_cart_uid_pid")
    public JsonResult<Cart> getCartByUidPid(Integer uid,Integer pid){
        Cart result = cartService.getByUidAndPid(uid, pid);
        return new JsonResult<>(Code.SELECT_OK,"购物车里有该商品!",result);
    }

    @RequestMapping("/insert")
    public JsonResult<Void> insert(HttpSession session,Cart cart){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        cartService.addProductToCart(uid,username,cart);
        return new JsonResult<>(Code.UPDATE_OK,"成功添加到购物车!");
    }

    @RequestMapping("/update")
    public JsonResult<Void> update(HttpSession session,Cart cart){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        cartService.updateCart(uid,username,cart);
        return new JsonResult<>(Code.UPDATE_OK,"成功更新购物车中的商品信息!");
    }

    @RequestMapping("/select_uid")
    public JsonResult<List<CartDTO>> selectByUid(Integer uid){
        List<CartDTO> carts = cartService.getByUid(uid);
        return new JsonResult<>(Code.SELECT_OK,"查询购物车的商品列表成功!",carts);
    }

    @RequestMapping("/delete")
    public JsonResult<Void> delete(@RequestBody List<Integer> listCid){
        System.out.println(listCid);
        cartService.batchDelete(listCid);
        return new JsonResult<>(Code.DEL_OK,"删除成功!");
    }
}
