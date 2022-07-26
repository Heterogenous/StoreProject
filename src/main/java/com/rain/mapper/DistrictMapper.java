package com.rain.mapper;

import com.rain.entity.District;

import java.util.List;

/** 省市区的持久层 **/
public interface DistrictMapper {
    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 某个父区域下的所有区域列表
     */
    List<District> findByParent(String parent);
}
