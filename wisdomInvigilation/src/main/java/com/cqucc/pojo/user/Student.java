package com.cqucc.pojo.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Student{
    private Integer id;                         //唯一识别id
    private String username;                    //账号（学号）
    private String password;                    //密码
    private String name;                        //真实姓名
    private String college;                     //学院
    private Integer grade;                      //年级
    private String profession;                  //专业
    private Integer banji;                      //班级
    private String img;                         //头像路径

    public Student() {
    }

    public Student(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Student(Integer id, String name, String college, Integer grade, String profession, Integer banji, String img) {
        this.id = id;
        this.name = name;
        this.college = college;
        this.grade = grade;
        this.profession = profession;
        this.banji = banji;
        this.img = img;
    }
}
