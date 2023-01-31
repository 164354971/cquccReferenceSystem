package com.cqucc.service.serviceImpl.exam.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.admin.ExamMapper;
import com.cqucc.pojo.exam.admin.Exam;
import com.cqucc.service.exam.admin.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {
    @Autowired
    private ExamMapper examMapper;

    @Override
    public List<Exam> list(Wrapper<Exam> queryWrapper) {
        return examMapper.selectList(queryWrapper);
    }
}
