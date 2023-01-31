package com.cqucc.pojo.exam.teacher;

import lombok.Data;

@Data
public class ExamTeacher {
    private Integer id            ;//int auto_increment comment '主键'
    private Integer examId       ;//int         not null comment '考试任务表的主键id',
    private Integer banji         ;//int         null comment '考试班级',
    private String teacher       ;//varchar(20) null comment '监考教师',
    private String majorTeacher ;//varchar(20) null comment '主考教师'

    public ExamTeacher() {
    }

    public ExamTeacher(Integer id, Integer examId, Integer banji, String teacher, String majorTeacher) {
        this.id = id;
        this.examId = examId;
        this.banji = banji;
        this.teacher = teacher;
        this.majorTeacher = majorTeacher;
    }
}
