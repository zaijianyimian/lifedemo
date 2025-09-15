package com.example.admin.controller;

import com.example.admin.domain.Reports;
import com.example.admin.service.ReportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "举报信息管理",description = "管理举报信息")
@RequestMapping("/api/report")
public class ReportController {
    @Resource
    private ReportsService reportService;

    @GetMapping("/getallreports")
    @Operation(summary = "获取举报信息列表", description = "获取举报信息列表")
    public List<Reports> getReportList() {
        return reportService.list();
    }

    @GetMapping("/deletereport")
    @Operation(summary = "删除举报信息", description = "删除举报信息")
    public String deleteReport(@RequestParam("reportId") Integer id) {
        if (id == null) {
            return "{\"status\":\"failure\",\"message\":\"举报信息id不能为空\"}";
        }
        boolean result = reportService.removeById(id);
        if (result) {
            return "{\"status\":\"success\",\"message\":\"删除成功\"}";
        } else {
            return "{\"status\":\"failure\",\"message\":\"删除失败\"}";
        }
    }
}
