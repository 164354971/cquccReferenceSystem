package com.cqucc.pojo.exam.admin;

import lombok.Data;

@Data
public class Exam {
    private Integer id         ;//int auto_increment comment '主键'
    private Boolean online     ;//tinyint(1)  not null comment '0线下1线上',
    private String loc        ;//varchar(20) null comment '位置',
    private Integer room       ;//int         null comment '考场号',
    private String term       ;//varchar(20) not null comment '学期',
    private Integer week       ;//tinyint     not null comment '周次',
    private String startTime  ;//datetime    not null comment '开始时间',
    private String endTime    ;//datetime    not null comment '结束时间',
    private Integer time       ;//int         not null comment '考试时长',
    private Integer grade      ;//int         not null comment '年级',
    private String college    ;//varchar(20) not null comment '学院名称',
    private String profession ;//varchar(20) not null comment '专业名称',
    private String course     ;//varchar(20) not null comment '课程名称'
    private Boolean rebuild     ;//tinyint(1)  not null comment '0无重修生1有重修生',

    public Exam() {
    }

    public Exam(Integer id, Boolean online, String loc, Integer room, String term, Integer week, String startTime, String endTime, Integer time, Integer grade, String college, String profession, String course, Boolean rebuild) {
        this.id = id;
        this.online = online;
        this.loc = loc;
        this.room = room;
        this.term = term;
        this.week = week;
        this.startTime = startTime;
        this.endTime = endTime;
        this.time = time;
        this.grade = grade;
        this.college = college;
        this.profession = profession;
        this.course = course;
        this.rebuild = rebuild;
    }
}
