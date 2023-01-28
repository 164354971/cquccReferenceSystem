package com.cqucc.service.serviceImpl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.user.AdminMapper;
import com.cqucc.pojo.user.Admin;
import com.cqucc.service.user.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
}
