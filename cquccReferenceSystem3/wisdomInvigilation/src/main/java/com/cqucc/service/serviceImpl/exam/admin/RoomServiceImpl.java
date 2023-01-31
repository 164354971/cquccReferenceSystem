package com.cqucc.service.serviceImpl.exam.admin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqucc.mapper.exam.admin.RoomMapper;
import com.cqucc.pojo.exam.admin.Room;
import com.cqucc.service.exam.admin.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {
    @Autowired
    private RoomMapper roomMapper;
}
