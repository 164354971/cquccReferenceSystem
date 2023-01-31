package com.cqucc.pojo.exam.admin;

import lombok.Data;

@Data
public class Rebuild {
    private Integer id         ;//int auto_increment comment '主键'
    private Integer grade      ;//int         not null comment '原年级',
    private String college    ;//varchar(20) not null comment '原学院名称',
    private String profession ;//varchar(20) not null comment '原专业名称',
    private Integer banji      ;//int         not null comment '原班级',
    private String username   ;//varchar(15) not null comment '学号',
    private String name       ;//varchar(20) not null comment '姓名',
    private String course     ;//varchar(20) not null comment '课程名称',
    private Boolean deal       ;//tinyint(1)  null comment '0未参考1参考'

    public Rebuild() {
    }

    public Rebuild(Integer id, Integer grade, String college, String profession, Integer banji, String username, String name, String course, Boolean deal) {
        this.id = id;
        this.grade = grade;
        this.college = college;
        this.profession = profession;
        this.banji = banji;
        this.username = username;
        this.name = name;
        this.course = course;
        this.deal = deal;
    }
}
