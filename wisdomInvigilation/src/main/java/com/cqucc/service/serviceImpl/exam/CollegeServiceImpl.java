package com.cqucc.service.serviceImpl.exam;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.CollegeMapper;
import com.cqucc.pojo.exam.College;
import com.cqucc.service.exam.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {
    @Autowired
    private CollegeMapper collegeMapper;
}
