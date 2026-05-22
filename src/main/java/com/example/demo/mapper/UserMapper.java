package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.demo.entity.UserEn;


@Mapper
public interface UserMapper extends BaseMapper<UserEn>{

    // List<UserEn> searchComplexUsers(@Param("query") UserQueryCondition condition);
    
} 