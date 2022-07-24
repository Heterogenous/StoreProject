package com.rain.service;

import com.rain.entity.User;

/** 用户模块业务层接口 **/
public interface IUserService {
    /**
     * 用户注册
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登陆
     * @param username 登录名
     * @param password 密码
     * @return 登陆用户信息对象,没有为null
     */
    User login(String username,String password);

    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword
                        );
}
