package com.cqucc.pojo.data;

import lombok.Data;

@Data
public class QueryInfo {
    private String term;
    private String college;
    private String profession;
    private Integer grade;
    private String course;
    private Integer week;
    private Integer time;
    private String startTime;
    private String endTime;
    private Boolean rebuild;
    private Boolean online;

    public QueryInfo() {
    }

    public QueryInfo(String term, String college, String profession, Integer grade, String course, Integer week, Integer time, String startTime, String endTime, Boolean rebuild, Boolean online) {
        this.term = term;
        this.college = college;
        this.profession = profession;
        this.grade = grade;
        this.course = course;
        this.week = week;
        this.time = time;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rebuild = rebuild;
        this.online = online;
    }
}
