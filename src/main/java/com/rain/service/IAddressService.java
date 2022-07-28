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

    /**
     * 根据用户的aid修改该条收货地址为默认
     * @param uid 修改用户的uid
     * @param username 修改用户的username
     * @param aid 用户的收货地址的aid
     */
    void setAddressToDefaultByAid(Integer uid,String username,Integer aid);

    /**
     * 根据收货地址的aid删除收货地址，并且将下一个收货地址修改为默认地址
     * @param aid 即将被删除的收货地址的aid
     * @param nextAid 即将被修改为默认收货地址的aid
     */
    void deleteAddressByAid(Integer aid,Integer nextAid);

    /**
     * 根据收货地址的aid查询某条收货地址
     * @param aid 收货地址的aid
     * @return 相应的收货地址信息
     */
    Address getByAid(Integer aid);

    /**
     * 根据aid修改收货地址信息
     * @param address 修改后的收货地址信息
     * @param uid 修改者的uid
     * @param username 修改者的username
     */
    void updateAddress(Integer uid,String username,Address address);

}
