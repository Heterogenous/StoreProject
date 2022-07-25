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

    /**
     * 修改个人密码
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword
                        );

    /**
     * 根据用户的id查询用户的数据
     * @param uid
     * @return
     */
    User getByUid(Integer uid);

    /**
     * 修改者修改被修改者的信息
     * @param uid 修改者uid,即当前登陆者
     * @param username 修改者username
     * @param user 被修改者的uid,username,phone,email,gender
     */
    void changeInfo(Integer uid,String username,User user);

    /**
     * 修改个人头像
     * @param uid   被修改的uid
     * @param username 修改者的username
     * @param avatar   被修改的头像路径
     */
    void changeAvatar(Integer uid,String username,String avatar);
}
