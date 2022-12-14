package com.rain.mapper;

import com.rain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest//表示标注当前的类为测试类，不会随项目打包
@RunWith(SpringRunner.class)//表示启动这个单元测试类，参数必须是SpringRunner的实例类型
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper(){
        User user = new User();
        user.setUsername("root");
        user.setPassword("root");
        Integer result = userMapper.insert(user);
        System.out.println(result);
    }

    @Test
    public void testUserMapperFindByUsername(){
        User user = userMapper.findByUsername("root");
        System.out.println(user);
    }

    @Test
    public void testUpdateByUser(){
        User user = new User();
        user.setUid(1);
        user.setPassword("1234");
        user.setModifiedUser("管理员");
        user.setModifiedTime(new Date());
        Integer integer = userMapper.updateByUser(user);
        System.out.println(integer);
    }

    @Test
    public void findById(){
        User user = userMapper.findByUid(1);
        System.out.println(user.getModifiedUser());
    }
}
