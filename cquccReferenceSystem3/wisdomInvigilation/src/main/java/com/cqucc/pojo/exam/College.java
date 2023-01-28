package com.cqucc.pojo.exam;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class College {
    private Integer id   ;//int auto_increment comment '主键'
    private String college ;//varchar(32) not null comment '学院名称'
    private String foundTime ;//date        not null comment '成立日期',

    public College() {
    }

    public College(Integer id, String college, String foundTime) {
        this.id = id;
        this.college = college;
        this.foundTime = foundTime;
    }
}
