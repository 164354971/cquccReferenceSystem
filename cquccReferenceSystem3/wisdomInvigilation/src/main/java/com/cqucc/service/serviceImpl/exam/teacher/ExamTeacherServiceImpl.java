package com.cqucc.service.serviceImpl.exam.teacher;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.teacher.ExamTeacherMapper;
import com.cqucc.pojo.exam.teacher.ExamTeacher;
import com.cqucc.service.exam.teacher.ExamTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamTeacherServiceImpl extends ServiceImpl<ExamTeacherMapper, ExamTeacher> implements ExamTeacherService {
    @Autowired
    private ExamTeacherMapper examTeacherMapper;
}
