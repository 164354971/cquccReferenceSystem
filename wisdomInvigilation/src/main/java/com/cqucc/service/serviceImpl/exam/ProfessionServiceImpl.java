package com.cqucc.service.serviceImpl.exam;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.ProfessionMapper;
import com.cqucc.pojo.exam.Profession;
import com.cqucc.pojo.exam.Term;
import com.cqucc.service.exam.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionServiceImpl extends ServiceImpl<ProfessionMapper, Profession> implements ProfessionService {
    @Autowired
    private ProfessionMapper professionMapper;

    @Override
    public List<Profession> list(Wrapper<Profession> queryWrapper) {
        return professionMapper.selectList(queryWrapper);
    }
}
