package com.cqucc.pojo.exam;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Profession {
    private Integer id; // int         not null comment '主键',
    private Integer collegeId; // int         not null comment '学院的主键',
    private Integer grade     ; // int         not null comment '学院的年级',
    private String profession; // varchar(32) not null comment '该学院年级开设的专业'
    private Integer year;       //int       not null comment '几年制'
    private String foundTime ;//date        not null comment '成立日期',

    public Profession() {
    }

    public Profession(Integer id, Integer collegeId, Integer grade, String profession, Integer year, String foundTime) {
        this.id = id;
        this.collegeId = collegeId;
        this.grade = grade;
        this.profession = profession;
        this.year = year;
        this.foundTime = foundTime;
    }
}
