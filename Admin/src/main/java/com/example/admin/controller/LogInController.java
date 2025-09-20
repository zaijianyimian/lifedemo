package com.example.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.admin.domain.Admin;
import com.example.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *管理员登录
 */

@RestController
@Tag(name="管理员登录", description="管理员登录接口")
@RequestMapping("/api/login")
public class LogInController {
    @Resource
    private AdminService adminService;

    @PostMapping("/adminlogin")
    @Operation(summary="管理员登录", description="管理员登录接口")
    public String adminLogin(@RequestParam("name") String name,
                             @RequestParam("password") String password) {
        QueryWrapper<Admin> qr = new QueryWrapper<>();
        qr.eq("name", name)
                .eq("password", password);
        Admin admin = adminService.getOne(qr);
        if (admin == null) {
            return "{\"status\":\"failure\"}";
        }
        return "{\"status\":\"success\"}";
    }

}
