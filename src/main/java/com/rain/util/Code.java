package com.rain.util;
/** 请求状态码 **/
public class Code {
    //POST请求成功
    public static final Integer POST_OK = 20010;
    public static final Integer POST_FAIL = 20011;

    //GET请求成功
    public static final Integer GET_OK = 30010;
    public static final Integer GET_FAIL = 30011;

    //DELETE请求成功
    public static final Integer DELETE_OK = 40010;
    public static final Integer DELETE_FAIL = 40011;

    //PUT请求成功
    public static final Integer PUT_OK = 50010;
    public static final Integer PUT_FAIL = 50011;

    //系统异常
    public static final Integer SYSTEM_ERROR = 60001;



    //文件上传异常
    public static final Integer FILE_UPLOAD_EMPTY = 100;
    public static final Integer FILE_UPLOAD_SIZE = 101;
    public static final Integer FILE_UPLOAD_STATE = 102;
    public static final Integer FILE_UPLOAD_TYPE = 103;
    public static final Integer FILE_UPLOAD_IO = 104;


    //注册成功与失败
    public static final Integer REG_OK = 200;
    public static final Integer REG_FAIL = 201;
    //注册异常
    public static final Integer REG_ERROR = 202;

    //登陆成功与失败
    public static final Integer LOGIN_OK = 300;
    public static final Integer LOGIN_FAIL = 301;

    //更新成功与失败
    public static final Integer UPDATE_OK = 400;
    public static final Integer UPDATE_FAIL = 401;
    //更新异常
    public static final Integer UPDATE_ERROR = 402;

    //查询成功与失败
    public static final Integer SELECT_OK = 500;
    public static final Integer SELECT_FAIL = 501;
    //查询异常
    public static final Integer SELECT_ERROR = 502;

    //删除成功异常
    public static final Integer DEL_OK = 600;
    public static final Integer DEL_ERROR = 602;

}
