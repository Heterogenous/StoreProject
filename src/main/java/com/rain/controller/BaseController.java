package com.rain.controller;

import com.rain.service.ex.*;
import com.rain.util.Code;
import com.rain.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/** 控制层类的基类 **/
public class BaseController {

    //请求处理的方法，这个方法的返回值就是需要传递给全端的数据
    //自动将异常对象传递给此方法的参列表上
    @ExceptionHandler(ServiceException.class)//用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(Code.REG_FAIL);
        }else if(e instanceof InsertException){
            result.setState(Code.REG_ERROR);
        }else if(e instanceof UserNotFoundException || e instanceof PasswordNotMatchException){
            result.setState(Code.LOGIN_FAIL);
        }
        else{
            result.setState(500);
        }
        result.setMessage(e.getMessage());
        return result;
    }

    /**
     * 获取Session对象中的uid
     * @param session
     * @return
     */
    protected final Integer getUidFromSession(HttpSession session){
       return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前登陆用户的登录名
     * @param session session对象
     * @return 
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
