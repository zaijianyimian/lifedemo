package com.example.admin.controller;

import com.example.admin.domain.Admin1;
import com.example.admin.manage.AdminManage;
import com.example.admin.service.Admin1Service;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员类，仅在测试时使用，生产环境中应使用OAuth2或JWT等安全机制
 */
@RestController
@RequestMapping("/admin1")
@Slf4j
public class Admin1Controller {

    @Resource
    private Admin1Service admin1Service;

    @GetMapping("/list")
    public List<Admin1> list() {
        return admin1Service.list();
    }
    @PostMapping("/add")
    public void add(@RequestParam("usernaem") String username,
                    @RequestParam("password") String password){
        Admin1 admin = new Admin1();
        admin.setUsername(username);
        admin.setPassword(password);
        admin1Service.saveAdminDetails(admin);
    }
}
