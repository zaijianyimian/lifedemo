package com.example.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.domain.Joins;
import com.example.user.service.JoinService;

import com.example.user.mapper.JoinMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lenovo
* @description 针对表【join】的数据库操作Service实现
* @createDate 2025-09-13 09:23:11
*/
@Service
public class JoinServiceImpl extends ServiceImpl<JoinMapper, Joins>
    implements JoinService {
    @Resource
    private JoinMapper joinMapper;


    @Override
    public List<Integer> selectTop3ActivityIds() {
        return joinMapper.selectTop3ActivityIds();
    }
}




