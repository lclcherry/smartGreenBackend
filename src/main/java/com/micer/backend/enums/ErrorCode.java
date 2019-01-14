package com.micer.backend.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode
{
    OK_COMMON(200_0000, "success"),
    
    // 400 BadRequest
    PARAM_ERR_COMMON(400_1000, "Bad Request"),
    PARAM_ERR_REQUEST_DATA_FIELD_UNPASS(400_1001, "请求数据字段验证不通过"),
    PARAM_ERR_REQUEST_DATA_REQUIED_FIELD_IS_NULL(400_1002, "请求数据必须字段不可为空"),
    
    // 403 Forbidden
    
    
    // 404 Not Found
    NOT_FOUND_COMMON(404_0000, "Not Found"),
    
    
    // 503 服务器错误
    
    ;
    
    private int code;
    private String msg;
    
    ErrorCode(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }
    
    /**
     * 获取相应的http状态码，用于设置返回response的status
     * @return
     */
    public HttpStatus getHttpStatus()
    {
        HttpStatus status = HttpStatus.valueOf(code / 1000);
        return status;
    }
}
