package com.example.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.user.domain.Comments;
import com.example.user.domain.Users;
import com.example.user.service.CommentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 评论内容管理
 */
@RestController
@RequestMapping("/api/comment")
@Tag(name = "评论管理",description = "评论管理相关接口")
public class CommentController {
    @Resource
    private CommentsService commentService;

    @PostMapping("/addcomment")
    @Operation(summary = "添加评论",description = "添加评论接口")
    public String addComment(@RequestParam("activityid") Integer activityid,
                            @RequestParam("content") String content,
                             HttpSession session){
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return "{\"status\": \"failure\", \"message\": \"请先登录！\"}";
        }
        try {
            String username = user.getName();
            Comments comments = new Comments();
            comments.setActivityId(activityid);
            comments.setCommentText(content);
            comments.setUserName(username);
            boolean save = commentService.save(comments);
            if (save) {
                return "{\"status\": \"success\", \"message\": \"评论成功！\"}";
            } else {
                return "{\"status\": \"failure\", \"message\": \"评论失败！\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"failure\", \"message\": \"系统异常！\"}";
        }
    }


    @PostMapping("/deletecomment")
    @Operation(summary = "删除评论",description = "删除评论接口")
    public String deleteComment(@RequestParam("commentId") Integer commentid){

        try{
            boolean delete = commentService.removeById(commentid);
        if (delete) {
            return "{\"status\": \"success\", \"message\": \"删除成功！\"}";
        } else {
            return "{\"status\": \"failure\", \"message\": \"删除失败！\"}";
        }}catch (Exception e){
            e.printStackTrace();
            return "{\"status\": \"failure\", \"message\": \"系统异常！\"}";
        }
    }
    @GetMapping("/getcomments")
    @Operation(summary = "获取评论列表",description = "获取评论列表接口")
    public List<Comments> getComments(@RequestParam("activity_id") Integer activityid){
        try {
            QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("activity_id",activityid);
            queryWrapper.orderByDesc("comment_date");
            List<Comments> comments = commentService.list(queryWrapper);
            return comments;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
