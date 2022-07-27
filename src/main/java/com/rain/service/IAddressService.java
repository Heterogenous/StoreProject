package com.rain.service;

import com.rain.entity.Address;

import java.util.List;

/** 收货地址业务层接口 **/
public interface IAddressService {
    /**
     * 添加收货地址信息
     * @param uid 添加者的uid
     * @param username 修改者的username
     * @param address 添加收获地址的信息
     * @return 受影响的行数
     */
    void addNewAddress(Integer uid, String username, Address address);

    /**
     * 根据用户的uid查询用户的收货地址信息
     * @param uid 用户的uid
     * @return 收货地址对象列表
     */
    List<Address> getByUid(Integer uid);

}
