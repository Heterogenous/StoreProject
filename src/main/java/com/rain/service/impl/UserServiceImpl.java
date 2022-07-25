package com.rain.service.impl;

import com.rain.entity.User;
import com.rain.mapper.UserMapper;
import com.rain.service.IUserService;
import com.rain.service.ex.*;
import com.rain.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/** 用户模块业务层的实现类 **/
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册的业务逻辑
     * @param user 用户的数据对象
     */
    @Override
    public void reg(User user) {
        //通过User获取Username
        String username = user.getUsername();
        //调用UserMapper的findByUsername(String username)判断用户是否被注册
        User result = userMapper.findByUsername(username);
        //判断结果集是否为null,不是则抛出用户名被占用异常
        if(result!=null){
            //抛出异常
            throw new UsernameDuplicatedException("用户名已被注册!", Code.REG_FAIL);
        }

        //密码加密处理
        //处理过程:随机字符串(盐值)+password+随机字符串(盐值) 之后将整个交给md5算法进行加密处理
        String oldPassword = user.getPassword();
        //获取盐值(随机生成一个盐值)
        String salt = UUID.randomUUID().toString().toUpperCase();
        //将盐值设置到对象中
        user.setSalt(salt);
        //将密码和盐值作为一个整体进行加密,忽略原有密码强度提升了数据的安全性
        String md5Password = getMD5Password(oldPassword, salt);
        //将加密的密码设置到对象中
        user.setPassword(md5Password);

        //补全数据is_delete = 0
        user.setIsDelete(0);

        //4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //执行注册业务功能的实现
        Integer rows = userMapper.insert(user);
        if(rows != 1){
            throw new InsertException("在用户注册过程中产生了未知的异常!",Code.REG_ERROR);
        }
    }

    /**
     * 用户登陆的业务逻辑
     * @param username 登录名
     * @param password 密码
     * @return 用户登陆信息对象,没有返回null
     */
    @Override
    public User login(String username, String password) {
        //根据登陆名来查询用户是否存在
        User result = userMapper.findByUsername(username);
        if(result == null){
            throw new UserNotFoundException("用户名未注册!",Code.LOGIN_FAIL);
        }
        //判断该用户是否被删除
        if(result.getIsDelete() == 1){
            throw new UserNotFoundException("用户名已被删除!",Code.LOGIN_FAIL);
        }

        //检查密码是否匹配
        //获取加密的密码
        String encryptPassword = result.getPassword();

        //对用户的密码进行相同加密操作后再进行比较
        //获取盐值
        String salt = result.getSalt();
        //加密登陆密码
        String newPassword = getMD5Password(password,salt);
        //比较
        if(!encryptPassword.equals(newPassword)){
            throw new PasswordNotMatchException("密码错误!",Code.LOGIN_FAIL);
        }

        //返回对象必要的字段内容,用于提升性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        return user;
    }

    /**
     * 修改密码
     * @param uid 修改的uid
     * @param username 谁修改
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户名不存在或已删除!",Code.UPDATE_FAIL);
        }
        //原始密码和数据库中的旧密码进行比较
        String oldMd5Password = getMD5Password(oldPassword,result.getSalt());
        if(!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("原密码错误!",Code.UPDATE_FAIL);
        }
        //将新的密码设置到数据库中
        String newMd5Password = getMD5Password(newPassword,result.getSalt());
        User user = new User();
        user.setUid(uid);
        user.setPassword(newMd5Password);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        //调用DAO层
        Integer rows = userMapper.updateByUser(user);
        if(rows != 1){
            throw new UpdateException("rows:"+rows+",更新数据产生未知的异常!",Code.UPDATE_ERROR);
        }
    }

    /**
     * 根据UId查询对象信息
     * @param uid
     * @return
     */
    @Override
    public User getByUid(Integer uid) {
        //根据用户的UID查询用户的信息
        User result = userMapper.findByUid(uid);
        //判断用户是否存在
        if(result == null || result.getIsDelete() == 1){
            System.out.println("不存在或已删除");
            throw new UserNotFoundException("用户不存在或已被删除!",Code.UPDATE_FAIL);
        }
        //封装必要的数据
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    /**
     * 修改个人信息
     * @param uid 修改者uid,即当前登陆者
     * @param username 修改者username
     * @param user 被修改者的uid,username,phone,email,gender
     */
    @Override
    public void changeInfo(Integer uid,String username,User user) {
        //先判断修改者的uid，是否为null,有可能因为session过期导致
        if(uid == null){
            throw new UpdateException("登陆过期，请重新登陆！",Code.UPDATE_FAIL);
        }
        //根据修改者的uid查询是否被删除,getByUid已处理
        User loginUser = getByUid(uid);
        //根据被修改者的信息查看是否被删除
        User modifiedUser = getByUid(user.getUid());
        //将被修改者信息存入到modifiedUser
        modifiedUser.setPhone(user.getPhone());
        modifiedUser.setEmail(user.getEmail());
        modifiedUser.setGender(user.getGender());
        //将修改者的username以及时间存入到modifiedUser中
        modifiedUser.setModifiedUser(loginUser.getUsername());
        modifiedUser.setModifiedTime(new Date());
        //将被修改者放入到dao层,更新数据
        Integer integer = userMapper.updateByUser(modifiedUser);
        //判断是否修改成功
        if(integer != 1){
            throw new UpdateException("修改失败!修改数据产生未知的异常",Code.UPDATE_ERROR);
        }


    }

    /**
     * 个人头像的修改
     * @param uid   被修改的uid
     * @param username 修改者的username
     * @param avatar   被修改的头像路径
     */
    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        //查询当前的用户是否存在
        User result = getByUid(uid);
        //处理avatar



        //将封装头像以及uid，username
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(avatar);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        //调用数据层修改数据库
        Integer integer = userMapper.updateByUser(user);
        //判断是否修改成功
        if(integer != 1){
            throw new UpdateException("修改失败!修改数据产生未知的异常",Code.UPDATE_ERROR);
        }

    }


    /** 定义一个md5算法的加密处理 **/
    private String getMD5Password(String password,String salt){
        //MD5加密算法的方法调用
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password +salt).getBytes()).toUpperCase();
        }

        //返回加密之后的密码
        return password;
    }
}
