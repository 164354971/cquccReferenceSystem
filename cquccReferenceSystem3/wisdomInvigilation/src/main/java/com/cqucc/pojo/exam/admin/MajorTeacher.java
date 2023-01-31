package com.cqucc.pojo.exam.admin;

import lombok.Data;

@Data
public class MajorTeacher {
    private String course     ;//varchar(20) not null comment '课程',
    private String teacherId ;//varchar(20) not null comment '教师id',
    private String name       ;//varchar(20) not null comment '教师姓名',
    private String college    ;//varchar(20) not null comment '课程所属学院'

    public MajorTeacher() {
    }

    public MajorTeacher(String course, String teacherId, String name, String college) {
        this.course = course;
        this.teacherId = teacherId;
        this.name = name;
        this.college = college;
    }
}
