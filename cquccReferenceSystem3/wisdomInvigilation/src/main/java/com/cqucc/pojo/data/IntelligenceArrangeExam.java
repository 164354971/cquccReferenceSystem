package com.cqucc.pojo.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IntelligenceArrangeExam {
    private List<String> locs;
    private List<Integer> rooms;
    private List<Integer> personNums;
    private List<List<Integer>> classSums;
    private List<List<String>> rebuilds;
    private List<List<String>> teachers;

    public IntelligenceArrangeExam() {
    }

    public IntelligenceArrangeExam(List<String> locs, List<Integer> rooms, List<Integer> personNums, List<List<Integer>> classSums, List<List<String>> rebuilds, List<List<String>> teachers) {
        this.locs = locs;
        this.rooms = rooms;
        this.personNums = personNums;
        this.classSums = classSums;
        this.rebuilds = rebuilds;
        this.teachers = teachers;
    }
}
