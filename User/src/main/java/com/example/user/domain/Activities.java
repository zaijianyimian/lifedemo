package com.example.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName activities
 */
@TableName(value ="activities")
@Data
public class Activities {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String activityName;

    private Integer categoryId;

    private String activityDescription;

    private Date activityDate;
}