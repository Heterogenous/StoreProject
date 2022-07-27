package com.rain.service.ex;

public class DeleteException extends ServiceException{
    public DeleteException() {
        super();
    }

    public DeleteException(String message) {
        super(message);
    }

    public DeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteException(Throwable cause) {
        super(cause);
    }

    protected DeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DeleteException(String message, Integer state) {
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
