package com.rain.service.impl;

import com.rain.entity.District;
import com.rain.mapper.DistrictMapper;
import com.rain.service.IDistrictService;
import com.rain.service.ex.DistrictNotFoundException;
import com.rain.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        if(parent == "" || parent == null){
            throw new DistrictNotFoundException("传入的数据不能为空!",Code.SELECT_FAIL);
        }
        List<District> list = districtMapper.findByParent(parent);
        //System.out.println(list.size()==0);
        if(list == null || list.size() == 0){
            throw new DistrictNotFoundException("没有找到相关数据!请检查输入参数!",Code.SELECT_FAIL);
        }
        //无关数据设置为null
        for(District d : list){
            d.setId(null);
            d.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        String name = districtMapper.findNameByCode(code);
        return name;
    }


}
