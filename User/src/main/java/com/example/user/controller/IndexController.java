package com.example.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * 页面管理
 */
@Controller
@Tag(name = "IndexController", description = "实现单纯页面跳转功能")
public class IndexController {


    private static final String REDIS_KEY = "activityid";

    @GetMapping("/addactivity")
    public String about() {
        return "addactivity";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("id") String id, Model model) {
        model.addAttribute("activityid", id);
        return "detail";
    }

    @GetMapping("/editactivity")
    public String editactivity(@RequestParam("id") String id, Model model) {
        model.addAttribute("activityid", id);
        return "editactivity";
    }

    @GetMapping( value = {"/","/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/manage")
    public String manage() {
        return "manage";
    }

    @GetMapping("/reportForm")
    public String reportForm() {
        return "reportForm";
    }

    @GetMapping("/searchtext")
    public String searchtext(@RequestParam("query") String query, Model model) {
        model.addAttribute("query", query);
        return "searchtext";
    }

    @GetMapping("/Test")
    public String signup() {
        return "Test";
    }

}
