package com.example.user.controller;

import com.example.user.domain.Reports;
import com.example.user.domain.Users;
import com.example.user.service.ReportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 举报信息管理
 */
@RestController
@Tag(name="举报信息管理")
@RequestMapping("/api/report")
public class ReportController {
    @Resource
    private ReportsService reportsService;

    @PostMapping("/createreport")
    @Operation(summary = "添加举报信息", description = "添加举报信息")
    public String createReport(@RequestParam("activityid") String activityId,
                               @RequestParam("description") String description,
                               HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return "{\"status\": \"failure\", \"message\": \"请先登录\"}";
        }
        try{
            Integer userId = user.getId();
            Reports report = new Reports();
            report.setUserId(userId);
            report.setActivityId(Integer.parseInt(activityId));
            report.setReportDescription(description);
            boolean save = reportsService.save(report);
            if (save) {
                return "{\"status\": \"success\", \"message\": \"举报成功\"}";
            } else {
                return "{\"status\": \"failure\", \"message\": \"举报失败\"}";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"failure\", \"message\": \"举报失败\"}";
        }
    }
}
