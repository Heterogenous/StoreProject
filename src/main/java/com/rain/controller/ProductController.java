package com.rain.controller;

import com.rain.entity.Product;
import com.rain.service.IProductService;
import com.rain.util.Code;
import com.rain.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseController{

    @Autowired
    private IProductService productService;

    @RequestMapping("/hot_list")
    public JsonResult<List<Product>> getHotList(){
        List<Product> hotList = productService.getHotList();
        return new JsonResult<>(Code.SELECT_OK,"查询热门商品成功!",hotList);
    }

    @RequestMapping("/details")
    public JsonResult<Product> getProductById(Integer id){
        Product data = productService.getById(id);
        return new JsonResult<>(Code.SELECT_OK,"查询商品成功!",data);
    }
}
