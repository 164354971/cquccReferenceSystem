package com.cqucc.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//作为springmvc的异常处理器
@RestControllerAdvice
public class ProjectExceptionAdvice {
    //拦截所有的异常信息
    @ExceptionHandler(Exception.class)
    public R doException(Exception ex){
        //记录日志

        //通知运维

        //通知开发
        ex.printStackTrace();//控制台提示报错
        return new R(false, null, "返回数据异常，请检查或稍后再试");
    }
}
