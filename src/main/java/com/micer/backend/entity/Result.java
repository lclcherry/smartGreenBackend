package com.micer.backend.entity;

public class Result
{
    private int statusCode; // 状态码
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
    
    // 暂时开放2个set方法，后续迭代中会删掉这两个方法
    @Deprecated
    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Deprecated
    public void setData(Object data)
    {
        this.data = data;
    }
    
    // 建议用ResultBuilder的build方法来生成Result
    // 这样Result的状态能够维持一致性：要么没有Result，要么Result的状态是不可改变的 immutabel
    // 可以用 Result.OK().msg("success").data(data).build(); 的流式API方式来构建Result
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
    
    // 暂时粗粒度地定义几种消息状态，后面再细粒度自定义ErrorCode状态码
    public static ResultBuilder OK() {return new ResultBuilder(200, "OK");}
    public static ResultBuilder BadRequest() {return new ResultBuilder(400, "Bad Request");}
    public static ResultBuilder Forbidden() {return new ResultBuilder(403, "Forbidden");}
    public static ResultBuilder NotFound() {return new ResultBuilder(404, "Not Found");}
}
