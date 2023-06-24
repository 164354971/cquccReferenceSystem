package com.cqucc.pojo.exam;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Grade {
    private Integer id ;//int not null comment '主键',
    private Integer collegeId ;//int not null comment '学院的主键',
    private Integer grade      ;//int not null comment '学院的年级'
    private String foundTime ;//date        not null comment '成立日期',

    public Grade() {
    }

    public Grade(Integer id, Integer collegeId, Integer grade, String foundTime) {
        this.id = id;
        this.collegeId = collegeId;
        this.grade = grade;
        this.foundTime = foundTime;
    }
}
