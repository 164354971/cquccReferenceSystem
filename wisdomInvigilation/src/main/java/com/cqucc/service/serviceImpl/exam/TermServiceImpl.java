package com.cqucc.service.serviceImpl.exam;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.TermMapper;
import com.cqucc.pojo.exam.Term;
import com.cqucc.service.exam.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermServiceImpl extends ServiceImpl<TermMapper, Term> implements TermService {
    @Autowired
    private TermMapper termMapper;

    @Override
    public List<Term> list(Wrapper<Term> queryWrapper) {
        return termMapper.selectList(queryWrapper);
    }
}
