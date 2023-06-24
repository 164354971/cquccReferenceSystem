package com.cqucc.service.serviceImpl.exam.student;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.student.ExamStudentMapper;
import com.cqucc.pojo.exam.student.ExamStudent;
import com.cqucc.service.exam.student.ExamStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamStudentServiceImpl extends ServiceImpl<ExamStudentMapper, ExamStudent> implements ExamStudentService {
    @Autowired
    private ExamStudentMapper examStudentMapper;

    @Override
    public List<ExamStudent> list(Wrapper<ExamStudent> queryWrapper) {
        return examStudentMapper.selectList(queryWrapper);
    }
}
