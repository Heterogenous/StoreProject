package com.rain.service.ex;

public class CartNotFoundProductException extends ServiceException{
    public CartNotFoundProductException() {
        super();
    }

    public CartNotFoundProductException(String message) {
        super(message);
    }

    public CartNotFoundProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartNotFoundProductException(Throwable cause) {
        super(cause);
    }

    protected CartNotFoundProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CartNotFoundProductException(String message, Integer state) {
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
