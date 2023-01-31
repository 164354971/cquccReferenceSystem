package com.cqucc.pojo.data;

import com.cqucc.pojo.exam.admin.MajorTeacher;
import com.cqucc.pojo.user.Teacher;
import lombok.Data;

@Data
public class QueryData {
    private Object roomList;//Room列表
    private Object classSumList;//ClassSum列表
    private Object RebuildList;//Rebuild列表
    private Object teacherList;//Teacher列表
    private MajorTeacher majorTeacher;

    public QueryData() {
    }

    public QueryData(Object roomList, Object classSumList, Object rebuildList, Object teacherList, MajorTeacher majorTeacher) {
        this.roomList = roomList;
        this.classSumList = classSumList;
        RebuildList = rebuildList;
        this.teacherList = teacherList;
        this.majorTeacher = majorTeacher;
    }
}
