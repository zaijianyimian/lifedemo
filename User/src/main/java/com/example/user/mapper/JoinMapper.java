package com.example.user.mapper;

import com.example.user.domain.Joins;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author lenovo
* @description 针对表【join】的数据库操作Mapper
* @createDate 2025-09-13 09:23:11
* @Entity generator.domain.Join
*/
public interface JoinMapper extends BaseMapper<Joins> {
    @Select("SELECT activityid FROM `joins` GROUP BY activityid ORDER BY COUNT(activityid) DESC LIMIT 3")
    public List<Integer> selectTop3ActivityIds();
}




