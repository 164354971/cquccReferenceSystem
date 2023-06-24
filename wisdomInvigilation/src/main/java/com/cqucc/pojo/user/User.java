package com.cqucc.pojo.user;

import lombok.Data;

import java.util.Objects;

@Data
public class User {
    private Integer type;
    private String username;
    private String password;

    public User() {

    }

    public User(Integer type, String username, String password) {
        this.type = type;
        this.username = username;
        this.password = password;
    }

    public String isEmptyUser(){
        if(Objects.equals(username, "")) return "请输入用户名";
        if(Objects.equals(password, "")) return "请输入密码";
        return null;
    }
}
