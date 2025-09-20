package com.example.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面管理
 */
@Controller
public class IndexController {
    @GetMapping({"/", "/index", "/home"})
    public String index() {
        return "usermanagement";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/activitymanagement")
    public String activitymanagement() {
        return "activitymanagement";
    }
    @GetMapping("/reportsolve")
    public String reportsolve() {
        return "reportsolve";
    }
    @GetMapping("usermanagement")
    public String usermanagement() {
        return "usermanagement";
    }
}
