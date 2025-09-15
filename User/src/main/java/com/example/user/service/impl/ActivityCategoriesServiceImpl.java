package com.example.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.domain.ActivityCategories;
import com.example.user.service.ActivityCategoriesService;

import com.example.user.mapper.ActivityCategoriesMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【activity_categories】的数据库操作Service实现
* @createDate 2025-09-13 09:24:06
*/
@Service
public class ActivityCategoriesServiceImpl extends ServiceImpl<ActivityCategoriesMapper, ActivityCategories>
    implements ActivityCategoriesService {

}




