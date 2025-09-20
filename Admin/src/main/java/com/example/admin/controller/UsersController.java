package com.example.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.admin.domain.Users;
import com.example.admin.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
/**
 *用户信息管理，包括增删改查等操作，restful接口
 */

@RestController
@Tag(name = "用户管理", description = "用户管理相关接口")
@RequestMapping("/api/user")
public class UsersController {
    @Resource
    private UsersService userService;

    @GetMapping("/getalluser")
    @Operation(summary = "获取所有用户", description = "获取所有用户信息")
    public Object getAllUser() {
        return userService.list();
    }

    @GetMapping("/user/{name}")
    @Operation(summary = "搜索用户", description = "根据用户名搜索用户信息")
    public Users searchUser(@PathVariable(value = "name") String username) {
        if (username == null || username.trim().length() == 0) {
            return null;
        }
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        return userService.getOne(queryWrapper);
    }

    @PutMapping("/user")
    @Operation(summary = "更新用户", description = "更新用户信息")
    public String updateUser(@RequestBody Users user) {
        boolean save = userService.updateById(user);
        if (save) {
            return "{\"status\":\"success\"}";
        }
        else {
            return "{\"status\":\"failure\"}";
        }
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "删除用户", description = "根据用户ID删除用户信息")
    public String deleteUser(@PathVariable(value = "id") Integer id) {
        boolean remove = userService.removeById(id);
        if (remove) {
            return "{\"status\":\"success\"}";
        }
        else {
            return "{\"status\":\"failure\"}";
        }
    }

    @PostMapping("/adduser")
    @Operation(summary = "添加用户", description = "添加用户信息")
    public String addUser(@RequestParam("name") String name, @RequestParam("password") String password) {
        if(name == null || name.trim().length() == 0 || password == null || password.trim().length() == 0)
        {
            return "{\"status\":\"failure\"}";
        }
        Users user = new Users();
        user.setName(name);
        user.setPassword(password);
        boolean save = userService.save(user);
        if (save) {
            return "{\"status\":\"success\"}";
        }
        else {
            return "{\"status\":\"failure\"}";
        }
    }
}
