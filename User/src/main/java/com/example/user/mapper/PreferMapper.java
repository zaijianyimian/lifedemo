package com.example.user.mapper;

import com.example.user.domain.Prefer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author lenovo
* @description 针对表【prefer】的数据库操作Mapper
* @createDate 2025-09-13 09:23:05
* @Entity generator.domain.Prefer
*/
public interface PreferMapper extends BaseMapper<Prefer> {
    @Select("SELECT category_id FROM prefer WHERE user_id = #{userId}")
    public List<Integer> getUserPrefer(Integer userId);
}




