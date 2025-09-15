package com.example.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.domain.Users;
import com.example.user.service.UsersService;
import com.example.user.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【users】的数据库操作Service实现
* @createDate 2025-09-13 09:22:12
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService {

}




