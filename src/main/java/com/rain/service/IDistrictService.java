package com.rain.service;

import com.rain.entity.District;

import java.util.List;

public interface IDistrictService {

    /**
     * 根据父区域代号查询区域的信息
     * @param parent 父区域代号
     * @return 省市区信息
     */
    List<District> getByParent(String parent);

    /**
     * 根据区域代码查询对应名称
     * @param code 区域代码
     * @return 对应名称
     */
    String getNameByCode(String code);
}
