package com.rain.mapper;

import com.rain.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void testInsert(){
        Address address = new Address();
        address.setUid(6);
        address.setName("齐天大圣");
        address.setProvinceName("广东省");
        address.setCityName("广州市");
        address.setAreaName("南沙区广隆永隆六街11号302");
        address.setPhone("13610064039");
        Integer insert = addressMapper.insert(address);
        System.out.println(insert);
    }

    @Test
    public void testCount(){
        Integer integer = addressMapper.countByUid(6);
        System.out.println("------------>"+integer);
    }

    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(5);
        list.forEach(System.out::println);
    }

    @Test
    public void findByAid(){
        Address address = addressMapper.findByAid(3);
        System.out.println(address);
        System.out.println(address.getModifiedUser());
        System.out.println(address.getModifiedTime());
    }

    @Test
    public void updateIsDefaultToZero(){
//        Integer integer = addressMapper.updateIsDefaultToZero(8);
//        System.out.println(integer);
    }

    @Test
    public void updateAddressByAid(){
        Address address = new Address();
        address.setAid(3);
        address.setModifiedUser("管理员");
        address.setModifiedTime(new Date());
//        address.setProvinceName("广东省");
//        address.setCityName("广州市");
//        address.setAreaName("南沙区");
//        address.setAddress("广隆永隆六街五号305");
//        address.setIsDefault(1);
        address.setName("美猴王");
        Integer integer = addressMapper.updateAddress(address);
        System.out.println(integer);
    }
}
