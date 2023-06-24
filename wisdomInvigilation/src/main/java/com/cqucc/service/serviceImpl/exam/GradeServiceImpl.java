package com.cqucc.service.serviceImpl.exam;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.GradeMapper;
import com.cqucc.pojo.exam.Grade;
import com.cqucc.service.exam.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Autowired
    private GradeMapper gradeMapper;
}
