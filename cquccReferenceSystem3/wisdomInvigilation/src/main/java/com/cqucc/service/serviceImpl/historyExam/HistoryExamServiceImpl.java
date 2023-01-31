package com.cqucc.service.serviceImpl.historyExam;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.historyExam.HistoryExamMapper;
import com.cqucc.pojo.historyExam.HistoryExam;
import com.cqucc.service.historyExam.HistoryExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryExamServiceImpl extends ServiceImpl<HistoryExamMapper, HistoryExam> implements HistoryExamService {
    @Autowired
    private HistoryExamMapper historyExamMapper;
}
