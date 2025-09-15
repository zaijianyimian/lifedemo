package com.example.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.admin.domain.Activities;
import com.example.admin.service.ActivitiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "活动相关类", description = "提供活动相关的接口")
@RequestMapping("/api/activity")
public class ActivityController {
    @Resource
    private ActivitiesService activitiesService;


    @GetMapping("/getallactivity")
    @Operation(summary = "获取所有活动", description = "获取所有活动")
    public List<Activities> getAllActivity() {
        QueryWrapper<Activities> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return activitiesService.list(wrapper);
    }

    @GetMapping("/searchactivity")
    @Operation(summary = "通过id搜索活动", description = "搜索活动")
    public Activities searchActivity(@RequestParam(value = "id") Integer id) {
        if (id == null) {
            return null;
        }
        return activitiesService.getById(id);
    }
    @GetMapping("/deleteactivity")
    @Operation(summary = "通过id删除活动", description = "删除活动")
    public String deleteActivity(@RequestParam(value = "id") Integer id) {
        if (id == null) {
            return "{\"status\":\"failure\"}";
        }
        boolean b = activitiesService.removeById(id);
        if (b) {
            return "{\"status\":\"success\"}";
        } else {
            return "{\"status\":\"failure\"}";
        }
    }
}
