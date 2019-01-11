package com.micer.backend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制器增强
 * 用来捕获@RequestMapping的方法中所有抛出的BusinessException
 * 将error message 放入 JsonResult中，返回给前端
 * */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public JsonResult errorHandle(BusinessException e){
        logger.error(e.getMessage());
        JsonResult jsonResult = new JsonResult(e.getCode(), e.getMsg());
        return jsonResult;
    }
}
