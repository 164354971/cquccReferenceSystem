package com.cqucc.service.serviceImpl.exam;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.CourseMapper;
import com.cqucc.pojo.exam.Course;
import com.cqucc.pojo.exam.Profession;
import com.cqucc.service.exam.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> list(Wrapper<Course> queryWrapper) {
        return courseMapper.selectList(queryWrapper);
    }
}
