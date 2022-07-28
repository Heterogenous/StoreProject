package com.rain.controller;

import com.rain.entity.Address;
import com.rain.service.IAddressService;
import com.rain.util.Code;
import com.rain.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseController{

    @Autowired
    private IAddressService addressService;

    @RequestMapping("/add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid,username,address);
        return new JsonResult<>(Code.UPDATE_OK,"新增收货地址成功!");
    }

    @RequestMapping({"/",""})
    public JsonResult<List<Address>> getAddressListByUid(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<Address> data = addressService.getByUid(uid);
        return new JsonResult<>(Code.SELECT_OK,"获取收货地址列表成功!",data);
    }

    @RequestMapping("/update_address_to_default")
    public JsonResult<Void> updateAddressToDefault(HttpSession session,Integer aid){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.setAddressToDefaultByAid(uid,username,aid);
        return new JsonResult<>(Code.UPDATE_OK,"成功设置为默认地址!");
    }

    @RequestMapping("/delete")
    public JsonResult<Void> deleteAddressByAid(Integer aid,Integer nextAid){
        addressService.deleteAddressByAid(aid,nextAid);
        return new JsonResult<>(Code.DEL_OK,"删除成功!");
    }

    @RequestMapping("/select")
    public JsonResult<Address> selectAddressByAid(Integer aid){
        Address address = addressService.getByAid(aid);
        return new JsonResult<>(Code.SELECT_OK,"查询成功!",address);
    }

    @RequestMapping("/update")
    public JsonResult<Void> updateAddressByAid(HttpSession session,Address address){
        //获取当前修改者的uid以及username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //更新用户
        addressService.updateAddress(uid,username,address);
        return new JsonResult<>(Code.UPDATE_OK,"更新收货地址成功!");
    }
}
