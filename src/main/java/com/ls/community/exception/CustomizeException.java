package com.ls.community.exception;

public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode error){
        this.message = error.getMessage();
        this.code = error.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
