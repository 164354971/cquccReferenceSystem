package com.cqucc.pojo.exam.student;

import lombok.Data;

@Data
public class ExamStudent {
    private Integer id;                      //int auto_increment comment '主键'
    private Integer examId;                  //int                  not null comment 'exam的id',
    private Integer seat;                    //int                  not null comment '座位号',
    private String username;                 //varchar(15)          not null comment '学号',
    private String name;                     //varchar(20)          not null comment '学生姓名',
    private Integer banji;                   //int                  not null comment '班级',
    private String course;                   //varchar(32)          not null comment '课程名',
    private Boolean sign;                    //tinyint(1) default 0 null comment '签到状态0否1是',
    private Boolean cheat;                   //tinyint(1) default 0 null comment '是否违纪0否1是',
    private String cheatInfo;                //varchar(100)         null comment '违纪原因'

    public ExamStudent() {
    }

    public ExamStudent(Integer id, Integer examId, Integer seat, String username, String name, Integer banji, String course, Boolean sign, Boolean cheat, String cheatInfo) {
        this.id = id;
        this.examId = examId;
        this.seat = seat;
        this.username = username;
        this.name = name;
        this.banji = banji;
        this.course = course;
        this.sign = sign;
        this.cheat = cheat;
        this.cheatInfo = cheatInfo;
    }
}
