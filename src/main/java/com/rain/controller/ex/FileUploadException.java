package com.rain.controller.ex;

public class FileUploadException extends RuntimeException{
    private Integer state;

    public FileUploadException() {
        super();
    }

    public FileUploadException(String message,Integer state){
        super(message);
        this.state = state;
    }

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadException(Throwable cause) {
        super(cause);
    }

    protected FileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
