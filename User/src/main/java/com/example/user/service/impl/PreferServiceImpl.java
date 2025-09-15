package com.example.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.domain.Prefer;
import com.example.user.service.PreferService;

import com.example.user.mapper.PreferMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lenovo
* @description 针对表【prefer】的数据库操作Service实现
* @createDate 2025-09-13 09:23:05
*/
@Service
public class PreferServiceImpl extends ServiceImpl<PreferMapper, Prefer>
    implements PreferService {
    @Resource
    private PreferMapper preferMapper;

    @Override
    public List<Integer> getUserPrefer(Integer userId) {
        return preferMapper.getUserPrefer(userId);
    }
}




