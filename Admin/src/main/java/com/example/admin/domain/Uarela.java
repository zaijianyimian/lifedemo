package com.example.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName uarela
 */
@TableName(value ="uarela")
@Data
public class Uarela {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userid;

    private Integer activityid;
}