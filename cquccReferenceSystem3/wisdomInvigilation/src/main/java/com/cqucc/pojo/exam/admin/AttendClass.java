package com.cqucc.pojo.exam.admin;

import lombok.Data;

@Data
public class AttendClass {
    private Integer id         ;//int auto_increment comment '主键'
    private String loc        ;//varchar(20) null comment '位置',
    private Integer room       ;//int         null comment '教室号',
    private String startTime ;//datetime    not null comment '开始时间',
    private String endTime   ;//datetime    not null comment '结束时间',
    private Integer courseId  ;//int         not null comment 'course的id'

    public AttendClass() {
    }

    public AttendClass(Integer id, String loc, Integer room, String startTime, String endTime, Integer courseId) {
        this.id = id;
        this.loc = loc;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseId = courseId;
    }
}
