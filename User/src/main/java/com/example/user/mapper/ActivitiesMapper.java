package com.example.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.user.domain.Activities;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author lenovo
* @description 针对表【activities】的数据库操作Mapper
* @createDate 2025-09-13 09:24:31
* @Entity generator.domain.Activities
*/
public interface ActivitiesMapper extends BaseMapper<Activities> {
    @Select("SELECT * FROM activities WHERE MATCH(activity_name, activity_description) AGAINST(#{keyword})")
    List<Activities> searchByFullText(@Param("keyword") String keyword);
}




