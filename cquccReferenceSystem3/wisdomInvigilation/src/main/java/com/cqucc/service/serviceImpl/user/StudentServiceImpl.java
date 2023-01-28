package com.cqucc.service.serviceImpl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.user.StudentMapper;
import com.cqucc.pojo.user.Student;
import com.cqucc.service.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
}
