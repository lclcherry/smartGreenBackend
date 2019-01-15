package com.micer.backend.ex;

import com.micer.backend.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制器增强
 * 用来捕获@RequestMapping的方法中所有抛出的BusinessException
 * 将error message 放入 Result中，返回给前端
 * */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    /**
     * 自定义错误处理方法
     * @param BusinessException
     * @return Result
     * */
    public Result errorHandle(BusinessException e){
        //将错误信息输出到日志文件
        logger.error(e.getMessage());
        Result Result = new Result(e.getCode(), e.getMsg());
        return Result;
    }
}
