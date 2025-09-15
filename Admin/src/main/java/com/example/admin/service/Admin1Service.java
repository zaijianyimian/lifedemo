package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.domain.Admin1;

/**
* @author lenovo
* @description 针对表【admin1】的数据库操作Service
* @createDate 2025-09-14 19:46:57
*/
public interface Admin1Service extends IService<Admin1> {
    void saveAdminDetails(Admin1 admin1);
}
