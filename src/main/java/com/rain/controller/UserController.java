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

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/reg")
    public JsonResult<Void> reg(User user){
        //创建响应结果对象
        userService.reg(user);
        return new JsonResult<>(Code.REG_OK,"注册成功!");
    }
}