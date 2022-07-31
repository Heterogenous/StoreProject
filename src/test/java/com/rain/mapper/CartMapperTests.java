package com.rain.mapper;

import com.rain.entity.Cart;
import com.rain.entity.CartDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartMapperTests {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void findByUidAndPid(){
        Cart cart = cartMapper.findByUidAndPid(1, 1);
        System.out.println(cart);
    }

    @Test
    public void updateCart(){
        Cart cart = new Cart();
        cart.setCid(2);
        cart.setNum(12);
        cart.setPrice(1200L);
        Integer integer = cartMapper.updateCart(cart);
        System.out.println(integer);
    }

    @Test
    public void findByUid(){
        List<Cart> carts = cartMapper.findByUid(8);
        System.out.println(carts);
    }

    @Test
    public void batchDelete(){
        List<Integer> listCid = new ArrayList<>();
        listCid.add(1);
        listCid.add(2);
        listCid.add(3);
        Integer rows = cartMapper.batchDelete(listCid);
        System.out.println(rows);
    }

    @Test
    public void batchSelect(){
        List<Integer> listCid = new ArrayList<>();
        listCid.add(53);
        listCid.add(55);
        List<CartDTO> list = cartMapper.batchSelect(listCid);
//        list.forEach(System.out::println);
        System.out.println(list.size());
    }
}
