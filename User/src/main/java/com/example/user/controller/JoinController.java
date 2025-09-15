package com.example.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.user.domain.Activities;
import com.example.user.domain.Joins;
import com.example.user.domain.Prefer;
import com.example.user.domain.Users;
import com.example.user.service.ActivitiesService;
import com.example.user.service.JoinService;
import com.example.user.service.PreferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 用户加入活动管理
 */
@RestController
@Tag(name = "管理用户加入活动信息",description = "管理用户加入活动信息的接口")
@RequestMapping("/api/join")
public class JoinController {
    @Resource
    private JoinService joinService;
    @Resource
    private ActivitiesService activitiesService;
    @Resource
    private PreferService preferService;
    @PostMapping("/addjoin")
    @Operation(summary = "添加用户加入活动信息",description = "添加用户加入活动信息的接口")
    public String addJoin(@RequestParam("activityid") Integer activityid,
                          @RequestParam("categoryId") Integer categoryId,
                          HttpSession session){
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return "{\"status\":\"failure\", \"message\":\"请先登录\"}";
        }
        try{
            Integer userid = user.getId();
            Joins joins1 = new Joins();
            joins1.setUserid(userid);
            joins1.setActivityid(activityid);
            boolean save = joinService.save(joins1);
            Prefer prefer = new Prefer();
            prefer.setUserId(userid);
            prefer.setCategoryId(categoryId);
            preferService.save(prefer);
            if(save){
                return "{\"status\":\"success\", \"message\":\"加入活动成功\"}";
            }else{
                return "{\"status\":\"failure\", \"message\":\"加入活动失败\"}";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{\"status\":\"success\", \"message\":\"加入活动失败\"}";
        }
    }
    @PostMapping("/deletejoin")
    @Operation(summary = "删除用户加入活动信息",description = "删除用户加入活动信息的接口")
    public String deleteJoin(@RequestParam("activityid") Integer activityid,
                             HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return "{\"status\":\"failure\", \"message\":\"请先登录\"}";
        }
        try {
            Integer userid = user.getId();
            QueryWrapper<Joins> qr = new QueryWrapper<>();
            qr.eq("userid", userid);
            qr.eq("activityid", activityid);
            boolean delete = joinService.remove(qr);
            if (delete) {
                return "{\"status\":\"success\", \"message\":\"删除成功\"}";
            } else {
                return "{\"status\":\"failure\", \"message\":\"删除失败\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\":\"failure\", \"message\":\"删除失败\"}";
        }
    }
        @GetMapping("/getalljoins")
        @Operation(summary = "获取用户加入的所有活动信息",description = "获取用户加入的所有活动信息的接口")
    public List<Activities> getAllJoins(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return Collections.emptyList();
        }
        try {
            QueryWrapper<Joins> qr = new QueryWrapper<>();
            qr.select("DISTINCT activityid")  // 使用select方法指定DISTINCT字段[1,4](@ref)
                    .eq("userid", user.getId());    // 添加查询条件

            List<Integer> activityids = joinService.list(qr)
                    .stream()
                    .map(Joins::getActivityid)
                    .toList();
          QueryWrapper<Activities> qr1 = new QueryWrapper<>();
          qr1.in("id", activityids)
                  .orderByDesc("id");
            List<Activities> list = activitiesService.list(qr1);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    @GetMapping("/isjoin")
    @Operation(summary = "判断用户是否加入活动",description = "判断用户是否加入活动的接口")
    public String isJoin(@RequestParam("activityid") Integer activityid,
                          HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return "{\"status\":\"failure\", \"message\":\"请先登录\"}";
        }
        try {
            Integer userid = user.getId();
            QueryWrapper<Joins> qr = new QueryWrapper<>();
            qr.eq("userid", userid);
            qr.eq("activityid", activityid);
            if (joinService.count(qr) > 0) {
                return "{\"status\":\"success\", \"message\":\"已加入活动\"}";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "{\"status\":\"failure\", \"message\":\"无法判断是否加入活动\"}";
        }
        return "{\"status\":\"failure \":\"未加入活动\"}";
    }
}
