package com.cqucc.pojo.data;

import com.cqucc.pojo.exam.admin.ClassSum;
import com.cqucc.pojo.exam.admin.MajorTeacher;
import com.cqucc.pojo.exam.admin.Rebuild;
import com.cqucc.pojo.exam.admin.Room;
import com.cqucc.pojo.user.Teacher;
import lombok.Data;

import java.util.List;

@Data
public class QueryData {
    private List<Room> roomList;//Room列表
    private List<ClassSum> classSumList;//ClassSum列表
    private List<Rebuild> rebuildList;//Rebuild列表
    private List<Teacher> teacherList;//Teacher列表
    private MajorTeacher majorTeacher;
    private Integer roomSum;

    public QueryData() {
    }

    public QueryData(List<Room> roomList, List<ClassSum> classSumList, List<Rebuild> rebuildList, List<Teacher> teacherList, MajorTeacher majorTeacher) {
        this.roomList = roomList;
        this.classSumList = classSumList;
        this.rebuildList = rebuildList;
        this.teacherList = teacherList;
        this.majorTeacher = majorTeacher;
    }

    public QueryData(List<Room> roomList, List<ClassSum> classSumList, List<Rebuild> rebuildList, List<Teacher> teacherList, MajorTeacher majorTeacher, Integer roomSum) {
        this.roomList = roomList;
        this.classSumList = classSumList;
        this.rebuildList = rebuildList;
        this.teacherList = teacherList;
        this.majorTeacher = majorTeacher;
        this.roomSum = roomSum;
    }
}
