package com.cqucc.pojo.user;

import lombok.Data;

@Data
public class Teacher {
    private Integer id         ;//int auto_increment comment '主键id'
    private String name       ;//varchar(20)                  not null comment '教师姓名',
    private String username   ;//varchar(15)                  not null comment '工号',
    private String password   ;//varchar(32) default '123456' not null comment '登录密码',
    private String college    ;//varchar(20)                  not null comment '学院名称',
    private String closeTime ;//date                         null comment '本学期结课时间',
    private String tel        ;//char(11)                     not null comment '联系电话',
    private String type       ;//varchar(10)                  not null comment '教师类型',
    private Integer examCount ;//int         default 0        not null comment '学期监考次数',

    public Teacher() {
    }

    public Teacher(Integer id, String name, String username, String password, String college, String closeTime, String tel, String type, Integer examCount) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.college = college;
        this.closeTime = closeTime;
        this.tel = tel;
        this.type = type;
        this.examCount = examCount;
    }
}
