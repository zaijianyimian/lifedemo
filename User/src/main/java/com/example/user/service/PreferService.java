package com.example.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.user.domain.Prefer;

import java.util.List;

/**
* @author lenovo
* @description 针对表【prefer】的数据库操作Service
* @createDate 2025-09-13 09:23:05
*/
public interface PreferService extends IService<Prefer> {
    public List<Integer> getUserPrefer(Integer userId);
}
