package com.rain.controller;

import com.rain.controller.ex.*;
import com.rain.service.ex.*;
import com.rain.util.Code;
import com.rain.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/** 控制层类的基类 **/
public class BaseController {

    //请求处理的方法，这个方法的返回值就是需要传递给全端的数据
    //自动将异常对象传递给此方法的参列表上
    @ExceptionHandler({ServiceException.class,FileUploadException.class})//用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(((UsernameDuplicatedException) e).getState());
        }else if(e instanceof InsertException){
            result.setState(((InsertException) e).getState());
        }else if(e instanceof UserNotFoundException || e instanceof PasswordNotMatchException){
            result.setState(((ServiceException) e).getState());
        } else if (e instanceof UpdateException) {
            result.setState(((UpdateException) e).getState());
        } else if (e instanceof FileEmptyException) {
            result.setState(((FileEmptyException) e).getState());
        } else if (e instanceof FileSizeException) {
            result.setState(((FileSizeException) e).getState());
        } else if (e instanceof FileTypeException) {
            result.setState(((FileTypeException) e).getState());
        } else if (e instanceof FileStateException) {
            result.setState(((FileStateException) e).getState());
        } else if (e instanceof FileUploadIOException) {
            result.setState(((FileUploadIOException) e).getState());
        } else if (e instanceof AddressCountLimitException) {
            result.setState(((AddressCountLimitException) e).getState());
        } else if (e instanceof DistrictNotFoundException) {
            result.setState(((DistrictNotFoundException) e).getState());
        } else if(e instanceof DeleteException){
            result.setState(((DeleteException) e).getState());
        } else if (e instanceof AddressNotFoundException) {
            result.setState(((AddressNotFoundException) e).getState());
        } else if (e instanceof ProductNotFoundException) {
            result.setState(((ProductNotFoundException) e).getState());
        } else{
            result.setState(Code.SYSTEM_ERROR);
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
