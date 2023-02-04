package com.cqucc.pojo.exam.admin;

import lombok.Data;

@Data
public class ClassSum implements Comparable<ClassSum>{
    private Integer id         ;//int auto_increment comment '主键'
    private Integer grade      ;//int         not null comment '年级',
    private String college    ;//varchar(20) not null comment '学院名称',
    private String profession ;//varchar(20) not null comment '专业名称',
    private Integer banji      ;//int         not null comment '班级',
    private Integer sum        ;//int         not null comment '人数'

    public ClassSum() {
    }

    public ClassSum(Integer id, Integer grade, String college, String profession, Integer banji, Integer sum) {
        this.id = id;
        this.grade = grade;
        this.college = college;
        this.profession = profession;
        this.banji = banji;
        this.sum = sum;
    }

    @Override
    public int compareTo(ClassSum o) {
        return o.sum.compareTo(this.sum);
    }
}
