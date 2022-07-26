package com.rain.service;

import com.rain.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {
    @Autowired
    private IAddressService addressService;

    @Test
    public void testAddAddress(){
        Address address = new Address();
        address.setUid(6);
        address.setName("美猴王");
        address.setProvinceName("广东省");
        address.setCityName("广州市");
        address.setAreaName("南沙区金隆小学");
        address.setPhone("17199910220");
        address.setCreatedUser("lily11");
        address.setModifiedUser("lily11");
        Date date = new Date();
        address.setCreatedTime(date);
        address.setModifiedTime(date);
        addressService.addNewAddress(6,"lily11",address);
    }
}
