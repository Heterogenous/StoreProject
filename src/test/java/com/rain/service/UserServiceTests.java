package com.rain.service;

import com.rain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired
    private IUserService iUserService;

    @Test
    public void testReg(){
        try {
            User user = new User();
            user.setUsername("Jack");
            user.setPassword("1234");
            iUserService.reg(user);
            System.out.println("200 success");
        } catch (Exception e) {
            //获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取具体异常的描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testLogin(){
        User user = iUserService.login("lily", "123456");
        System.out.println(user);
    }

    @Test
    public void testChangePassword(){
        iUserService.changePassword(5,"lily","123456","1234");
    }

    @Test
    public void testGetByUid(){
        User user = iUserService.getByUid(6);
        System.out.println(user);
    }

    @Test
    public void testChangeInfo(){
        User user = new User();
        user.setUid(6);
        user.setPhone("13719197549");
        user.setEmail("1328763823@qq.com");
        user.setGender(0);
        user.setModifiedUser("root");
        user.setModifiedTime(new Date());
        iUserService.changeInfo(2,"tom",user);
    }
}
