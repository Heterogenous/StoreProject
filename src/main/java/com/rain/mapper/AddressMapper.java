package com.rain.mapper;

import com.rain.entity.Address;

import java.util.List;

/** 收获地址模块持久层接口 **/
public interface AddressMapper {
    /**
     * 插入用户的收货地址
     * @param address 收获地址对象
     * @return 受影响行数
     */
    Integer insert(Address address);

    /**
     * 根据用户uid查询收货地址数量
     * @param uid 用户的uid
     * @return 收获地址的数量
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户的uid查询用户的收货地址信息
     * @param uid 用户的uid
     * @return 收货地址对象列表
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据地址对象的aid查询收货地址
     * @param aid 地址对象的aid
     * @return 对应的地址对象
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户的uid修改相应的收货地址，其中的默认值全为0
     * @param address
     * @return
     */
    Integer updateIsDefaultToZero(Address address);

    /**
     * 根据收货地址的aid修改Address信息
     * @param address
     * @return
     */
    Integer updateAddress(Address address);

    /**
     * 根据收货地址aid删除对象
     * @param aid 收货地址的唯一aid
     * @return 受影响的行数
     */
    Integer deleteAddress(Integer aid);
}
