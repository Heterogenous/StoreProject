package com.rain.service.ex;
/** 业务层异常的基类
 *  根据业务层不同功能来详细定义具体的异常类型，统一继承ServiceException
 * **/
public class ServiceException extends RuntimeException {

    private Integer state;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    //添加状态码用于区分是在何种场景下触发的异常

    public ServiceException(String message,Integer state){
        super(message);
        this.state = state;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
