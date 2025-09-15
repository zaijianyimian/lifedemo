package com.example.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.domain.Comments;
import com.example.user.service.CommentsService;

import com.example.user.mapper.CommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【comments】的数据库操作Service实现
* @createDate 2025-09-13 09:23:20
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
    implements CommentsService {

}




