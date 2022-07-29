package com.rain.mapper;

import com.rain.entity.Product;

import java.util.List;

public interface ProductMapper {

    /**
     * 查询前4天热门产品
     * @return
     */
    List<Product> findHotList();

    /**
     * 根据id查询产品信息
     * @return
     */
    Product findById(Integer id);
}
