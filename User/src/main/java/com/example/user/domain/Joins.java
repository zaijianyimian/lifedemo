package com.example.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName join
 */
@TableName(value ="joins")
@Data
public class Joins {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userid;

    private Integer activityid;
}