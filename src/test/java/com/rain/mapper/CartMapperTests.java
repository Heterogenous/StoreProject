package com.rain.mapper;

import com.rain.entity.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
