package com.rain.service.impl;

import com.rain.entity.Address;
import com.rain.mapper.AddressMapper;
import com.rain.service.IAddressService;
import com.rain.service.ex.AddressCountLimitException;
import com.rain.service.ex.InsertException;
import com.rain.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;//收货地址业务接口

    @Autowired
    private DistrictServiceImpl districtService;//省市区列表业务接口

    @Value("${address.max-count}")
    private Integer MAX_ADDRESS_COUNT ;

    /**
     *
     * @param uid 登录者的uid
     * @param username 登录者的username
     * @param address 添加收获地址的信息
     * @return
     */
    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //判断收货地址条数是否已经达到上限
        Integer count = addressMapper.countByUid(uid);
        //System.out.println("MAX_ADDRESS_COUNT--->"+MAX_ADDRESS_COUNT);
        if(count > MAX_ADDRESS_COUNT){
            throw new AddressCountLimitException("收货地址已达上限!", Code.UPDATE_FAIL);
        }
        //将所缺数据设置到address对象中
        //省市区,从前端获取省市区的code,再调用省市区表获取对应的名称
        address.setProvinceName(districtService.getNameByCode(address.getProvinceCode()));//省
        address.setCityName(districtService.getNameByCode(address.getCityCode()));//市
        address.setAreaName(districtService.getNameByCode(address.getAreaCode()));//区
        //uid,是否设置为默认
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        //日志
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        Date date = new Date();
        address.setCreatedTime(date);
        address.setModifiedTime(date);
        //调用持久层方法，添加收货地址
        Integer result = addressMapper.insert(address);
        if(result != 1){
            throw new InsertException("添加失败!",Code.UPDATE_FAIL);
        }
    }
}
