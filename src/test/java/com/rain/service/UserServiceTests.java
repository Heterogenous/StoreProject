package com.rain.service;

import com.rain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
