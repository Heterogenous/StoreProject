package com.rain.service.impl;

import com.rain.entity.User;
import com.rain.mapper.UserMapper;
import com.rain.service.IUserService;
import com.rain.service.ex.InsertException;
import com.rain.service.ex.UsernameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/** 用户模块业务层的实现类 **/
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        //通过User获取Username
        String username = user.getUsername();
        //调用UserMapper的findByUsername(String username)判断用户是否被注册
        User result = userMapper.findByUsername(username);
        //判断结果集是否为null,不是则抛出用户名被占用异常
        if(result!=null){
            //抛出异常
            throw new UsernameDuplicatedException("用户名已被注册!");
        }

        //密码加密处理
        //处理过程:随机字符串(盐值)+password+随机字符串(盐值) 之后将整个交给md5算法进行加密处理
        String oldPassword = user.getPassword();
        //获取盐值(随机生成一个盐值)
        String salt = UUID.randomUUID().toString().toUpperCase();
        //将盐值设置到对象中
        user.setSalt(salt);
        //将密码和盐值作为一个整体进行加密,忽略原有密码强度提升了数据的安全性
        String md5Password = getMD5Password(oldPassword, salt);
        //将加密的密码设置到对象中
        user.setPassword(md5Password);

        //补全数据is_delete = 0
        user.setIsDelete(0);

        //4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //执行注册业务功能的实现
        Integer rows = userMapper.insert(user);
        if(rows != 1){
            throw new InsertException("在用户注册过程中产生了未知的异常!");
        }
    }

    /** 定义一个md5算法的加密处理 **/
    private String getMD5Password(String password,String salt){
        //MD5加密算法的方法调用
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password +salt).getBytes()).toUpperCase();
        }

        //返回加密之后的密码
        return password;
    }
}
