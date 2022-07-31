package com.rain.service.impl;

import com.rain.entity.Address;
import com.rain.mapper.AddressMapper;
import com.rain.service.IAddressService;
import com.rain.service.ex.*;
import com.rain.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
            throw new InsertException("添加异常!",Code.UPDATE_ERROR);
        }
    }


    /**
     * 根据用户的uid查询收货地址列表
     * @param uid 用户的uid
     * @return 收货地址列表
     */
    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        if(list.size() == 0 || list == null ){
            throw new AddressNotFoundException("查询不到该用户的地址!",Code.SELECT_FAIL);
        }
        //将不需要的值设置为空
        for(Address address : list){
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            //address.setTel(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
        }
        return list;
    }

    /**
     * 修改用户的某条收货地址为默认值
     * @param uid 修改用户的uid
     * @param username 修改用户的username
     * @param aid 用户的收货地址的aid
     */
    @Override
    public void setAddressToDefaultByAid(Integer uid, String username, Integer aid) {
        //修改该用户所有收货地址的默认值为0
        Address updateDefaultToZero = new Address();
        updateDefaultToZero.setUid(uid);
        updateDefaultToZero.setModifiedUser(username);
        updateDefaultToZero.setModifiedTime(new Date());
        Integer rows = addressMapper.updateIsDefaultToZero(updateDefaultToZero);
        if(rows < 1){
            throw new UpdateException("修改所有收货地址为默认值为非默认异常!",Code.UPDATE_ERROR);
        }
        //根据aid修改用户该条收货地址为默认
        Address updateDefault = new Address();
        updateDefault.setAid(aid);
        updateDefault.setIsDefault(1);
        updateDefault.setModifiedTime(new Date());
        updateDefault.setModifiedUser(username);
        Integer integer = addressMapper.updateAddress(updateDefault);
        if(integer != 1 ){
            throw new UpdateException("修改收货地址为默认异常!",Code.UPDATE_ERROR);
        }
    }

    /**
     * 根据收货地址的aid删除收货地址，并且将下一个收货地址修改为默认地址
     * @param aid 即将被删除的收货地址的aid
     * @param nextAid 即将被修改为默认收货地址的aid
     */
    @Override
    public void deleteAddressByAid(Integer aid, Integer nextAid) {
        //删除当前收货地址
        Integer rows = addressMapper.deleteAddress(aid);
        //System.out.println("删除的条数:--------->"+rows);
        if(rows != 1){
            throw new DeleteException("收货地址删除异常!",Code.DEL_ERROR);
        }

        //先判断nextAid不等于-1，即执行将下一个收货地址设置为默认
        if(nextAid != -1){
            Address updateDefault = new Address();
            updateDefault.setAid(nextAid);
            updateDefault.setIsDefault(1);
            updateDefault.setModifiedUser("系统自动设置");
            updateDefault.setModifiedTime(new Date());
            Integer integer = addressMapper.updateAddress(updateDefault);
            if(integer != 1){
                throw new UpdateException("设置收货地址为默认异常",Code.UPDATE_ERROR);
            }
        }
    }

    /**
     * 根据收货地址的aid查询某条收货地址
     * @param aid 收货地址的aid
     * @return 相应的收货地址信息
     */
    @Override
    public Address getByAid(Integer aid) {
        //根据aid获取收货地址对象
        Address result = addressMapper.findByAid(aid);
        if(result == null){
            throw new AddressNotFoundException("查不到该收货地址!",Code.SELECT_FAIL);
        }
        //将不需要的信息置空
        result.setModifiedUser(null);
        result.setModifiedTime(null);
        result.setCreatedUser(null);
        result.setCreatedTime(null);
        return result;
    }

    @Override
    public void updateAddress(Integer uid, String username, Address address) {
        //先判断该aid是否存在这样的收货地址
        if(addressMapper.findByAid(address.getAid()) == null){
            throw new AddressNotFoundException("修改失败，找不到该地址!",Code.UPDATE_FAIL);
        }
        //省市区,从前端获取省市区的code,再调用省市区表获取对应的名称
        address.setProvinceName(districtService.getNameByCode(address.getProvinceCode()));//省
        address.setCityName(districtService.getNameByCode(address.getCityCode()));//市
        address.setAreaName(districtService.getNameByCode(address.getAreaCode()));//区
        //日志
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());
        //调用更新操作
        Integer rows = addressMapper.updateAddress(address);
        if (rows != 1 ){
            throw new UpdateException("更新收货地址异常!",Code.UPDATE_ERROR);
        }
    }


}
