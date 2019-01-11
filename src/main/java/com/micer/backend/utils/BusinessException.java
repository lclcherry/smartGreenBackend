package com.micer.backend.utils;

/**
 * 自定义异常类，继承RuntimeException
 * code设为"1"
 *
 * */
public class BusinessException extends RuntimeException{
    private static long serialVersionUID = 1L;

    private String code;
    private String msg;

    public BusinessException(){}

    public BusinessException(String code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
