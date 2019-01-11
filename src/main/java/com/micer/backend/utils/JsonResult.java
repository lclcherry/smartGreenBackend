package com.micer.backend.utils;

/**
 * 统一返回格式
 * */
public class JsonResult<T> {
    private T data;
    //成功为 "0", 失败为 "1"
    private String code;
    private String msg;

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param code
     * @param msg
     */
    public JsonResult(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为"0"，默认提示信息为：success
     * @param data
     */
    public JsonResult(T data){
        this.data = data;
        this.code = "0";
        this.msg = "success";
    }

    /**
     * 有数据返回，状态码为"0"，人为指定提示信息
     * @param data
     * @param msg
     */
    public JsonResult(T data, String msg){
        this.data = data;
        this.code = "0";
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
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
