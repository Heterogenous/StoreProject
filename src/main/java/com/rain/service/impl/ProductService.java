package com.rain.service.impl;

import com.rain.entity.Product;
import com.rain.mapper.ProductMapper;
import com.rain.service.IProductService;
import com.rain.service.ex.ProductNotFoundException;
import com.rain.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 查询前4热门商品
     * @return
     */
    @Override
    public List<Product> getHotList() {
        List<Product> hotList = productMapper.findHotList();
        if (hotList == null || hotList.size() == 0){
            throw new ProductNotFoundException("查询不到相关产品!", Code.SELECT_FAIL);
        }
        return hotList;
    }

    @Override
    public Product getById(Integer id) {
        Product result = productMapper.findById(id);
        if(result == null){
            throw new ProductNotFoundException("查询不到该商品!",Code.SELECT_FAIL);
        }
        //清空不需要的信息
        result.setCreatedUser(null);
        result.setCreatedTime(null);
        result.setModifiedUser(null);
        result.setModifiedTime(null);
        return result;
    }
}
