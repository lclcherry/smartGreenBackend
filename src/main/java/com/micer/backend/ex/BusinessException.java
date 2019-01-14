package com.micer.backend.ex;

/**
 * 自定义异常类，继承RuntimeException
 * code设为"1"
 *
 * */
public class BusinessException extends RuntimeException{
    private static long serialVersionUID = 1L;

    private int code;
    private String msg;

    public BusinessException(){}

    public BusinessException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    
    public int getCode()
    {
        return code;
    }
    
    public void setCode(int code)
    {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
