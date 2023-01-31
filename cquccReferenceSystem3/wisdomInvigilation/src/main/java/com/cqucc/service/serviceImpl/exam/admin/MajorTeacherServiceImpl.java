package com.cqucc.service.serviceImpl.exam.admin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.admin.MajorTeacherMapper;
import com.cqucc.pojo.exam.admin.MajorTeacher;
import com.cqucc.service.exam.admin.MajorTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MajorTeacherServiceImpl extends ServiceImpl<MajorTeacherMapper, MajorTeacher> implements MajorTeacherService {
    @Autowired
    private MajorTeacherMapper mapper;
}
