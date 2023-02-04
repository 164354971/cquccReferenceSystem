package com.cqucc.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class ObjectToList {
    public static <T> List<T> objectToList(Object obj, Class<T> clazz) {
        // 判断 obj 是否包含 List 类型
        if (obj instanceof List<?>) {
            String s = "";
            List<T> List = (java.util.List<T>) obj;
            s = JSON.toJSONString(List);
            List = JSON.parseArray(s, clazz);
            return List;
        }
        return null;
    }
}
