package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.admin.domain.Admin1;
import com.example.admin.manage.AdminManage;
import com.example.admin.mapper.Admin1Mapper;
import com.example.admin.service.Admin1Service;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【admin1】的数据库操作Service实现
* @createDate 2025-09-14 19:46:57
*/
@Service
public class Admin1ServiceImpl extends ServiceImpl<Admin1Mapper, Admin1>
    implements Admin1Service {

    @Resource
    private AdminManage adminManage;

    @Override
    public void saveAdminDetails(Admin1 admin1) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withDefaultPasswordEncoder()
                .username(admin1.getUsername()) //自定义用户名
                .password(admin1.getPassword()) //自定义密码
                .build();
        adminManage.createUser(userDetails);
    }
}




