package com.cqucc.pojo.data;

import com.cqucc.pojo.exam.admin.ClassSum;
import com.cqucc.pojo.exam.admin.Rebuild;
import com.cqucc.pojo.user.Teacher;
import lombok.Data;

import java.util.List;

@Data
public class QueryExam {
    private List<Integer> classSumList;
    private String loc;
    private Integer room;
    private Integer num1;
    private Integer num2;
    private Integer personNum;
    private List<String> rebuildList;
    private List<String> teacherList;

    public QueryExam() {
    }

    public QueryExam(List<Integer> classSumList, Integer personNum, List<String> teacherList) {
        this.classSumList = classSumList;
        this.personNum = personNum;
        this.teacherList = teacherList;
    }

    public QueryExam(List<Integer> classSumList, Integer personNum, List<String> rebuildList, List<String> teacherList) {
        this.classSumList = classSumList;
        this.personNum = personNum;
        this.rebuildList = rebuildList;
        this.teacherList = teacherList;
    }

    public QueryExam(List<Integer> classSumList, String loc, Integer room, Integer num1, Integer num2, Integer personNum, List<String> teacherList) {
        this.classSumList = classSumList;
        this.loc = loc;
        this.room = room;
        this.num1 = num1;
        this.num2 = num2;
        this.personNum = personNum;
        this.teacherList = teacherList;
    }

    public QueryExam(List<Integer> classSumList, String loc, Integer room, Integer num1, Integer num2, Integer personNum, List<String> rebuildList, List<String> teacherList) {
        this.classSumList = classSumList;
        this.loc = loc;
        this.room = room;
        this.num1 = num1;
        this.num2 = num2;
        this.personNum = personNum;
        this.rebuildList = rebuildList;
        this.teacherList = teacherList;
    }
}
