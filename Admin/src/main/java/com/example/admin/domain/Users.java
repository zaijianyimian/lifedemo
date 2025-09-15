package com.example.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName users
 */
@TableName(value ="users")
@Data
public class Users {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String password;
}