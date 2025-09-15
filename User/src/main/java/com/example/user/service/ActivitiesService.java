package com.example.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.user.domain.Activities;

import java.util.List;

/**
* @author lenovo
* @description 针对表【activities】的数据库操作Service
* @createDate 2025-09-13 09:24:31
*/
public interface ActivitiesService extends IService<Activities> {
    public List<Activities> searchByFullText(String keyword);
}
