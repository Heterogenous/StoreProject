package com.rain.mapper;

import com.rain.entity.Address;

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
}
