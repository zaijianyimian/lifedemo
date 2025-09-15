package com.example.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.user.domain.Prefer;
import com.example.user.domain.Users;
import com.example.user.service.PreferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户偏好管理
 */
@RestController
@RequestMapping("/api/prefer")
@Slf4j
@Tag(name="对用户偏好管理的接口",description="对用户偏好管理的接口")
public class PreFerController {
    @Resource
    private PreferService preferService;

    @DeleteMapping("/prefers/{category_id}")
    @Operation(summary="删除用户偏好",description="删除用户偏好")
    public String deletePrefers(@PathVariable("category_id") Integer categoryId, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return "{\"status\": \"failure\", \"message\": \"请先登录\"}";
        }
        try {
            Integer userId = user.getId();
            QueryWrapper<Prefer> qr = new QueryWrapper<>();
            qr.eq("user_id", userId);
            qr.eq("category_id", categoryId);
            log.error(userId + "\t" + categoryId);
            boolean b = preferService.remove(qr);
            if (b) {
                return "{\"status\": \"success\", \"message\": \"删除成功\"}";
            } else {
                return "{\"status\": \"failure\", \"message\": \"删除失败,可能该偏好不存在或已被删除\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"error\", \"message\": \"服务器错误: " + e.getMessage() + "\"}";
        }
    }
    @PostMapping("/prefers/{category_id}")
    @Operation(summary="添加用户偏好",description="添加用户偏好")
    public String addPrefers(@PathVariable("category_id") Integer categoryId, @RequestParam("content") String content, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return "{\"status\": \"failure\", \"message\": \"请先登录\"}";
        }
        try {
            Integer userId = user.getId();
            Prefer prefer = new Prefer();
            prefer.setUserId(userId);
            prefer.setCategoryId(categoryId);
            boolean b = preferService.save(prefer);
            if (b) {
                return "{\"status\": \"success\", \"message\": \"添加成功\"}";
            } else {
                return "{\"status\": \"failure\", \"message\": \"添加失败,请检查输入是否正确\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"error\", \"message\": \"服务器错误: " + e.getMessage() + "\"}";
        }
    }

}
