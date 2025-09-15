package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.admin.domain.Admin;
import com.example.admin.mapper.AdminMapper;
import com.example.admin.service.AdminService;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【admin】的数据库操作Service实现
* @createDate 2025-09-14 11:12:04
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService {

}




