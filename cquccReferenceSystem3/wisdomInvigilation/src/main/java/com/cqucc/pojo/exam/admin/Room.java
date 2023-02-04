package com.cqucc.pojo.exam.admin;

import lombok.Data;

@Data
public class Room{
    private String loc     ;//varchar(10)  not null comment '所在楼宇',
    private Integer room    ;//int         not null comment '教室号',
    private Integer num1    ;//int         not null comment '座位数',
    private Integer num2    ;//int         not null comment '建议座位数',
    private Integer longth1 ;//int         not null comment '第一部分长',
    private Integer longth2 ;//int         not null,
    private Integer longth3 ;//int         not null,
    private Integer width1  ;//int         not null comment '第一部分宽',
    private Integer width2  ;//int         not null,
    private Integer width3  ;//int         not null,

    public Room() {
    }

    public Room(String loc, Integer room, Integer num1, Integer num2, Integer longth1, Integer longth2, Integer longth3, Integer width1, Integer width2, Integer width3) {
        this.loc = loc;
        this.room = room;
        this.num1 = num1;
        this.num2 = num2;
        this.longth1 = longth1;
        this.longth2 = longth2;
        this.longth3 = longth3;
        this.width1 = width1;
        this.width2 = width2;
        this.width3 = width3;
    }

}
