package com.example.user.service;

import com.example.user.domain.Joins;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author lenovo
* @description 针对表【join】的数据库操作Service
* @createDate 2025-09-13 09:23:11
*/
public interface JoinService extends IService<Joins> {
    public List<Integer>  selectTop3ActivityIds();

}
