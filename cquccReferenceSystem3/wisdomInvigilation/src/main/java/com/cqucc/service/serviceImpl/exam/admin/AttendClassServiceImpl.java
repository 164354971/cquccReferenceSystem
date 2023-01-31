package com.cqucc.service.serviceImpl.exam.admin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.admin.AttendClassMapper;
import com.cqucc.pojo.exam.admin.AttendClass;
import com.cqucc.service.exam.admin.AttendClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendClassServiceImpl extends ServiceImpl<AttendClassMapper, AttendClass> implements AttendClassService {
    @Autowired
    private AttendClassMapper attendClassMapper;
}
