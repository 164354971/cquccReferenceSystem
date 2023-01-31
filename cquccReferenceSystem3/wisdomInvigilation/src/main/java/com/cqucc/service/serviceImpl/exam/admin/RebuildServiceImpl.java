package com.cqucc.service.serviceImpl.exam.admin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.admin.RebuildMapper;
import com.cqucc.pojo.exam.admin.Rebuild;
import com.cqucc.service.exam.admin.RebuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RebuildServiceImpl extends ServiceImpl<RebuildMapper, Rebuild> implements RebuildService {
    @Autowired
    private RebuildMapper rebuildMapper;
}
