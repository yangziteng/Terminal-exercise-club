package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.Association;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssociationDao extends BaseMapper<Association> {
}
