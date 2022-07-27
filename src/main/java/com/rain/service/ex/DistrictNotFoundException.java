package com.rain.service.ex;

public class DistrictNotFoundException extends  ServiceException{
    public DistrictNotFoundException() {
        super();
    }

    public DistrictNotFoundException(String message) {
        super(message);
    }

    public DistrictNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DistrictNotFoundException(Throwable cause) {
        super(cause);
    }

    protected DistrictNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DistrictNotFoundException(String message, Integer state) {
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
