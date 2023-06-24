package com.cqucc.pojo.exam;

import lombok.Data;

@Data
public class Course {
    private Integer id        ;              // int auto_increment comment '主键'
    private Integer grade     ;              // int                            not null comment '年级',
    private String college   ;              // varchar(20)                    not null comment '学院名称',
    private String profession;              // varchar(20)                    not null comment '专业名称',
    private String course    ;              // varchar(20)                    not null comment '课程名称',
    private String termStart;              // char(11) default '2023-2024-1' not null comment '学期',
    private String termEnd  ;              // char(11) default '2023-2024-2' not null comment '学期'

    public Course() {
    }

    public Course(Integer id, Integer grade, String college, String profession, String course, String termStart, String termEnd) {
        this.id = id;
        this.grade = grade;
        this.college = college;
        this.profession = profession;
        this.course = course;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }
}
