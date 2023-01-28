package com.cqucc.utils;

import lombok.Data;

import java.util.List;

@Data
public class R {
    //封装类，也称为前后端数据协议
    private Boolean flag;
    private Object data;
    private String msg;

    public R(){

    }

    public R(Boolean flag){
        this.flag = flag;
    }

    public R(Boolean flag, Object data){
        this.flag = flag;
        this.data = data;
    }

    public R(Boolean flag, Object data, String msg) {
        this.flag = flag;
        this.data = data;
        this.msg = msg;
    }

}
