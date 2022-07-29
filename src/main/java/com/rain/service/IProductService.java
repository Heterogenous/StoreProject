package com.rain.service;

import com.rain.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {

    /**
     * 获取前4条热门商品
     * @return
     */
    List<Product> getHotList();

    /**
     * 根据id查询商品的信息
     * @return
     */
    Product getById(Integer id);
}
