package com.cqucc.pojo.exam;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Timestamp;

@Data
public class Term {
    private Integer id         ;//int auto_increment comment '学期主键'
    private String term       ;//varchar(20) not null comment '学期',
    private String startTime ;//date        not null comment '起始日期',
    private String endTime ;//date        not null comment '结束日期',
    private Integer count      ;//int         not null comment '此学期周数'

    public Term() {
    }

    public Term(Integer id, String term, String startTime, String endTime, Integer count) {
        this.id = id;
        this.term = term;
        this.startTime = startTime;
        this.endTime = endTime;
        this.count = count;
    }

}
