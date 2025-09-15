package com.example.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName comments
 */
@TableName(value ="comments")
@Data
public class Comments {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userName;

    private Integer activityId;

    private String commentText;

    private Date commentDate;
}