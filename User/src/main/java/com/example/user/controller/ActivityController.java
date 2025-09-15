package com.example.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.user.domain.Activities;
import com.example.user.domain.Uarela;
import com.example.user.domain.Users;
import com.example.user.dto.SearchDTO;
import com.example.user.service.ActivitiesService;
import com.example.user.service.JoinService;
import com.example.user.service.PreferService;
import com.example.user.service.UarelaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 活动信息管理
 */
@RestController
@RequestMapping("/api/activity")
@Tag(name = "活动相关类", description = "提供活动相关的接口")
public class ActivityController {
    @Resource
    private ActivitiesService activitiesService;

    @Resource
    private UarelaService uarelaService;

    @Resource
    private JoinService joinService;

    @Resource
    private PreferService preferService;

    @PostMapping("/activity")//原先为@PostMapping("/addactivity")
    @Transactional
    @Operation(summary = "添加活动", description = "添加活动接口")
    public String addActivity(@RequestParam("activity_name") String activityName,
                              @RequestParam("category_id") int categoryId,
                              @RequestParam("activity_description")  String activityDescription,
            HttpSession session) {
       Users user = (Users) session.getAttribute("user");
       if(user == null){
           return "{\"status\": \"failure\", \"message\": \"请先登录\"}";
       }
       int userId = user.getId();
       try{
           String activityname = activityName.trim();
           String description = activityDescription.trim();
           if(activityname.isEmpty() || description.isEmpty()){
               return "{\"status\": \"failure\", \"message\": \"活动名称或描述不能为空\"}";
           }
           if(categoryId <= 0){
               return "{\"status\": \"failure\", \"message\": \"请选择活动分类\"}";
           }
               Activities activities = new Activities();
               activities.setActivityName(activityname);
               activities.setCategoryId(categoryId);
               activities.setActivityDescription(description);
               boolean save = activitiesService.save(activities);

           Uarela uarela = new Uarela();
           uarela.setUserid(userId);
           uarela.setActivityid(activities.getId());
           boolean save1 = uarelaService.save(uarela);

           if(save && save1) {
               return "{\"status\": \"success\", \"message\": \"添加活动成功\"}";
           }
       }catch (Exception e){
           e.printStackTrace();
           return "{\"status\": \"failure\", \"message\": \"添加活动失败\"}";
       }
       return "{\"status\": \"failure\", \"message\": \"添加活动失败\"}";
    }

    @GetMapping("/getTopActivities")
    @Operation(summary = "获取最热门的活动", description = "获取最热门的活动接口")
    public List<Activities> getTopActivities() {
        try {
            List<Integer> activitiesId = joinService.selectTop3ActivityIds();

            if (activitiesId == null || activitiesId.isEmpty()) {
                return Collections.emptyList();
            }
            List<Activities> activitiesList = new ArrayList<>();
            for (int i = 0; i < activitiesId.size(); i++) {
                Activities activities = activitiesService.getById(activitiesId.get(i));
                activitiesList.add(activities);
            }
            return activitiesList;
        } catch (Exception e) {
            e.printStackTrace(); // 临时保留，但建议替换为日志记录
            return Collections.emptyList();
        }
    }

    @GetMapping("/getactivitybycatid/{category_id}")
    @Operation(summary = "根据分类id获取活动", description = "根据分类id获取活动接口")
    public List<Activities> getActivityByCatId(@PathVariable("category_id") int categoryId) {
        if(categoryId < 0||categoryId > 5){
            return Collections.emptyList();
        }
        if(categoryId == 0){
            QueryWrapper<Activities> qr = new QueryWrapper<>();
            qr.orderByDesc("id");
            return activitiesService.list(qr);
        }
        try{
            QueryWrapper<Activities> qr = new QueryWrapper<>();
            qr.eq("category_id", categoryId)
                    .orderByDesc("id");
            List<Activities> activitiesList = activitiesService.list(qr);
            return activitiesList;
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }

    }
    @GetMapping("/getactbylistcat")
    @Operation(summary = "获取推荐活动", description = "获取推荐活动接口")
    public List<Activities> getActByListCat(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return Collections.emptyList();
        }
        int userId = user.getId();
        try{
            List<Integer> categoryIds = preferService.getUserPrefer(userId);
            if(categoryIds.isEmpty()){
                return Collections.emptyList();
            }
            QueryWrapper<Activities> qr = new QueryWrapper<>();
            qr.in("category_id", categoryIds);
            List<Activities> activitiesList = activitiesService.list(qr);
            return activitiesList;
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    @GetMapping("/activity/{id}")
    @Operation(summary = "根据活动id获取活动", description = "根据活动id获取活动接口")
    public Activities getActivityById(@PathVariable("id") int activityId) {
        try {
            Activities activities = activitiesService.getById(activityId);
            return activities;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping("/activity/{id}")
    @Operation(summary = "删除活动", description = "删除活动接口")
    public String deleteActivity(@PathVariable("id") int activityId) {
        try {
            boolean b = activitiesService.removeById(activityId);
            if(b){
                return "{\"status\": \"success\", \"message\": \"删除活动成功\"}";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"failure\", \"message\": \"删除活动失败\"}";
        }
        return "{\"status\": \"failure\", \"message\": \"删除活动失败\"}";
    }

    @GetMapping("/getpublishedactivities")
    @Operation(summary = "获取发布的活动", description = "获取发布的活动接口")
    public List<Activities> getPublishedActivities(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return Collections.emptyList();
        }
        int userId = user.getId();
        QueryWrapper<Uarela> qr1 = new QueryWrapper<>();
        qr1.eq("userid", userId)
                .select("activityid");
        List<Integer> activityIds = uarelaService.list(qr1).stream().map(Uarela::getActivityid).toList();
        if(activityIds.isEmpty()){
            return Collections.emptyList();
        }
        try{
            QueryWrapper<Activities> qr = new QueryWrapper<>();
            qr.in("id", activityIds)
                    .orderByDesc("id");
            List<Activities> activitiesList = activitiesService.list(qr);
            return activitiesList;
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    @PutMapping("/activity")
    @Operation(summary = "更新活动", description = "更新活动接口")
    public String updateActivity(@RequestParam("id") int activityId,
                                @RequestParam("activity_name") String activityName,
                                 @RequestParam("category_id") int categoryId,
                                 @RequestParam("activity_description")  String activityDescription){
        String activityname = activityName.trim();
        String description = activityDescription.trim();
        if(activityname.isEmpty() || description.isEmpty()){
            return "{\"status\": \"failure\", \"message\": \"活动名称或描述不能为空\"}";
        }
        if(categoryId <= 0){
            return "{\"status\": \"failure\", \"message\": \"请选择活动分类\"}";
        }
        try{
            Activities activities = new Activities();
            activities.setId(activityId);
            activities.setActivityName(activityname);
            activities.setCategoryId(categoryId);
            activities.setActivityDescription(description);
            boolean b = activitiesService.updateById(activities);
            if(b){
                return "{\"status\": \"success\", \"message\": \"更新活动成功\"}";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{\"status\": \"failure\", \"message\": \"更新活动失败\"}";
        }
        return "{\"status\": \"failure\", \"message\": \"更新活动失败\"}";
    }

    @GetMapping("/searchactname")
    @Operation(summary = "搜索活动名称", description = "搜索活动名称接口")
    public List<Activities> searchActName(@RequestParam("activityName") String activityName){
        if(activityName.isEmpty()){
            return Collections.emptyList();
        }
        try {
            boolean isLike = activityName.length() <= 3;
            if(isLike){
                QueryWrapper<Activities> qr = new QueryWrapper<>();
                qr.like("activity_name", activityName)
                        .or()
                        .like("activity_description", activityName)
                        .orderByDesc("id");
                List<Activities> list = activitiesService.list(qr);
                return list;
            }else{
                List<Activities> activities = activitiesService.searchByFullText(activityName);
                return activities;
            }
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}




