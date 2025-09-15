package com.example.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.domain.Activities;
import com.example.user.service.ActivitiesService;

import com.example.user.mapper.ActivitiesMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lenovo
* @description 针对表【activities】的数据库操作Service实现
* @createDate 2025-09-13 09:24:31
*/
@Service
public class ActivitiesServiceImpl extends ServiceImpl<ActivitiesMapper, Activities>
    implements ActivitiesService {
    @Resource
    private ActivitiesMapper activitiesMapper;

    @Override
    public List<Activities> searchByFullText(String keyword) {
        return activitiesMapper.searchByFullText(keyword);
    }
}




