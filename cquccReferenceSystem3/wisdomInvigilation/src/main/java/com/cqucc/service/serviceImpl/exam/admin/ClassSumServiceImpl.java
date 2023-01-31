package com.cqucc.service.serviceImpl.exam.admin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.admin.ClassSumMapper;
import com.cqucc.pojo.exam.admin.ClassSum;
import com.cqucc.service.exam.admin.ClassSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassSumServiceImpl extends ServiceImpl<ClassSumMapper, ClassSum> implements ClassSumService {
    @Autowired
    private ClassSumMapper classSumMapper;
}
