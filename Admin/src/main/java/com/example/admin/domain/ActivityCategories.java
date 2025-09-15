package com.example.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName activity_categories
 */
@TableName(value ="activity_categories")
@Data
public class ActivityCategories {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String categoryName;
}