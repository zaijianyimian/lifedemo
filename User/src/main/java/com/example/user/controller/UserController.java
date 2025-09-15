package com.example.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.user.domain.Joins;
import com.example.user.domain.Users;
import com.example.user.dto.UsersDTO;
import com.example.user.service.JoinService;
import com.example.user.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 用户模块设计
 */

@RestController
@Tag(name = "用户模块",description = "用户登录注册相关接口")
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Resource
    private UsersService userService;

    @Resource
    private JoinService joinService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        if(username == null || password == null){
           return  "{\"status\":\"false\", \"message\":\"用户名或密码不能为空\"}";
        }
        try{
            QueryWrapper<Users> qr = new QueryWrapper<>();
            qr.eq("name", username)
                    .eq("password", password);
            Users user = userService.getOne(qr);
            if(user == null){
                return "{\"status\":\"false\", \"message\":\"用户名或密码错误\"}";
            }
            session.setAttribute("user", user);
            return "{\"status\":\"true\", \"message\":\"登录成功\", \"userid\":\"" + user.getId() + "\"}";
        }catch (Exception e){
            return "{\"status\":\"false\", \"message\":\"系统异常\"}";
        }
    }
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password){
        if(username == null || password == null){
            return "{\"status\":\"false\", \"message\":\"用户名或密码不能为空\"}";
        }
        try{
            QueryWrapper<Users> qr = new QueryWrapper<>();
            qr.eq("name", username);
            Users user1 = userService.getOne(qr);
            if(user1 != null){
                return "{\"status\":\"false\", \"message\":\"用户名已存在\"}";
            }
            Users user = new Users();
            user.setName(username);
            user.setPassword(password);
            boolean save = userService.save(user);
            if(!save){
                return "{\"status\":\"false\", \"message\":\"注册失败\"}";
            }
            return "{\"status\":\"true\", \"message\":\"注册成功\"}";
        }catch (Exception e){
            e.printStackTrace();
            log.error(username + "注册失败"+password);
            return "{\"status\":\"false\", \"message\":\"系统异常\"}";
        }
    }

    @GetMapping("/getuserbyactivityid")
    @Operation(summary = "根据活动id获取参与者",description = "根据活动id获取参与者")
    public List<UsersDTO> getUserByActivityId(@RequestParam("activityId") Integer activityId){
        if(activityId == null){
            return Collections.emptyList();
        }
        try{
            QueryWrapper<Joins> qr = new QueryWrapper<>();
            qr.eq("activityid", activityId)
                    .select("userid");
            List<Integer> userIds = joinService.list(qr).stream().map(Joins::getUserid).toList();
            if(userIds.isEmpty()){
                return Collections.emptyList();
            }
            QueryWrapper<Users> qr1 = new QueryWrapper<>();
            qr1.in("id", userIds);
            List<Users> users = userService.list(qr1);
            List<UsersDTO> userDTOS = users.stream().map(user -> {
                UsersDTO dto = new UsersDTO();
                dto.setName(user.getName());
                return dto;
            }).toList();
            return userDTOS;
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据活动id获取参与者失败"+activityId);
            return Collections.emptyList();
        }
    }
    @GetMapping("/getuserinfo")
    @Operation(summary = "获取用户信息",description = "获取用户信息")
    public Users getUserInfo(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return null;
        }
        return user;
    }

    @PostMapping("/updateuser")
    @Operation(summary = "更新用户信息",description = "更新用户信息")
    public String updateUser(@RequestBody Users user,HttpSession session) {
        if (user == null) {
            return "{\"status\":\"failure\", \"message\":\"用户信息不能为空\"}";
        }
        try {
            Users user1 = (Users) session.getAttribute("user");
            if (user1 == null) {
                return "{\"status\":\"failure\", \"message\":\"用户未登录\"}";
            }
            user1.setName(user.getName());
            user1.setPassword(user.getPassword());
            boolean update = userService.updateById(user1);
            if (!update) {
                return "{\"status\":\"failure\", \"message\":\"更新失败\"}";
            }
            return "{\"status\":\"success\", \"message\":\"更新成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新用户信息失败" + user.getName());
            return "{\"status\":\"false\", \"message\":\"系统异常\"}";
        }
    }
}
