package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.terminalex.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
