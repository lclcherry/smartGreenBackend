package com.micer.backend.entity;

public class Result
{
    private int statusCode;
    private String msg;
    private Object data;
    
    public Result(int statusCode, String msg)
    {
        this.statusCode = statusCode;
        this.msg = msg;
    }
    
    public Result(int statusCode, String msg, Object data)
    {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }
    
    public int getStatusCode()
    {
        return statusCode;
    }
    
    public String getMsg()
    {
        return msg;
    }
    
    public Object getData()
    {
        return data;
    }
    
    public static class ResultBuilder
    {
        private int statusCode;
        private String msg;
        private Object data;
    
        public ResultBuilder(int statusCode, String msg)
        {
            this.statusCode = statusCode;
            this.msg = msg;
        }
        
        public ResultBuilder msg(String msg)
        {
            this.msg = msg;
            return this;
        }
        
        public ResultBuilder data(Object data)
        {
            this.data = data;
            return this;
        }
        
        public Result build()
        {
            return new Result(statusCode, msg, data);
        }
    
        public int getStatusCode()
        {
            return statusCode;
        }
    
        public String getMsg()
        {
            return msg;
        }
    
        public Object getData()
        {
            return data;
        }
    }
    
    // 暂时粗粒度地定义几种消息状态，后面再细粒度自定义状态码
    public static ResultBuilder OK() {return new ResultBuilder(200, "OK");}
    public static ResultBuilder BadRequest() {return new ResultBuilder(403, "Bad Request");}
    public static ResultBuilder NotFound() {return new ResultBuilder(404, "Not Found");}
}
