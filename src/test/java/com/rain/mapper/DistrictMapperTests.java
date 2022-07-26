package com.rain.mapper;

import com.rain.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictMapperTests {
    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void getByParent(){
        List<District> list = districtMapper.findByParent("86");
        list.forEach(System.out::println);
    }
}
