package com.rain.controller;

import com.rain.entity.User;
import com.rain.service.IUserService;
import com.rain.service.ex.InsertException;
import com.rain.service.ex.UsernameDuplicatedException;
import com.rain.util.Code;
import com.rain.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 注册请求
     * @param user
     * @return
     */
    @RequestMapping("/reg")
    public JsonResult<Void> reg(User user){
        //创建响应结果对象
        userService.reg(user);
        return new JsonResult<>(Code.REG_OK,"注册成功!");
    }


    /**
     * 登陆请求
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session){
        User user = userService.login(username, password);
        //将对象设置到session对象中
        session.setAttribute("uid",user.getUid());
        session.setAttribute("username",user.getUsername());
        return new JsonResult<>(Code.LOGIN_OK, "登陆成功!", user);
    }

    /**
     * 修改密码
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @param session session对象
     * @return 返回修改信息
     */
    @RequestMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session
                                           ){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(Code.UPDATE_OK,"修改成功!");
    }

//    @RequestMapping("/clear_session")
//    public void clearSession(HttpSession session){
//        session.setAttribute("uid",null);
//        session.setAttribute("username",null);
//        session.invalidate();
//        System.out.println(session);
//    }

    /**
     * 根据登陆信息，获取对象信息
     * @param session 登陆信息
     * @return 对应的信息
     */
    @RequestMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User result = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(Code.GET_OK,"获取对象成功!",result);
    }

    /**
     * 修改用户信息,phone,email,gender
     * @param user 被修改者的信息
     * @param session 登录者信息
     * @return 被修改者的状况
     */
    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //获取登录者的uid和username
        Integer loginUid = getUidFromSession(session);
        String loginUsername = getUsernameFromSession(session);
        //调用业务逻辑，将登录者信息以及被修改者信息传入
        userService.changeInfo(loginUid,loginUsername,user);
        return new JsonResult<>(Code.UPDATE_OK,"修改成功!");
    }

}
