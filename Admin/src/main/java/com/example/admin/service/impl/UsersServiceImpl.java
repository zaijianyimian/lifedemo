package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.domain.Users;
import com.example.admin.mapper.UsersMapper;
import com.example.admin.service.UsersService;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【users】的数据库操作Service实现
* @createDate 2025-09-14 11:12:28
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService {

}




