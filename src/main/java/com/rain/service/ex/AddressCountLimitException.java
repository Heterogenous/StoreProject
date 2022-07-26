package com.rain.service.ex;

/** 收获地址总数超出默认条数(20) **/
public class AddressCountLimitException extends ServiceException{
    public AddressCountLimitException() {
        super();
    }

    public AddressCountLimitException(String message) {
        super(message);
    }

    public AddressCountLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressCountLimitException(Throwable cause) {
        super(cause);
    }

    protected AddressCountLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AddressCountLimitException(String message, Integer state) {
        super(message, state);
    }

    @Override
    public Integer getState() {
        return super.getState();
    }

    @Override
    public void setState(Integer state) {
        super.setState(state);
    }
}
