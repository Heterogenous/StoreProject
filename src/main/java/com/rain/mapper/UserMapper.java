package com.rain.mapper;

import com.rain.entity.User;

/*
    用户模块的持久层接口
 */
public interface UserMapper {
    /**
     * 插入用户的数据
     * @param user 用户的数据
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户的数据
     * @param username 用户名
     * @return 如果找到对于的用户则返回这个用户的数据，如果没有，则返回null值
     */
    User findByUsername(String username);

    /**
     * 根据用户的uid修改用户信息
     * @param user 传入的参数
     * @return 改变的行数
     */
    Integer updateByUser(User user);

    /**
     * 根据用户的uid查询用户的数据
     * @param uid
     * @return
     */
    User findByUid(Integer uid);
}
