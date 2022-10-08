package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.ActivitiesBelongsDao;
import com.example.demo.domain.ActivitiesBelongs;
import com.example.demo.service.ActivitiesBelongsService;
import org.springframework.stereotype.Service;

@Service
public class ActivitiesBelongsServiceImpl extends ServiceImpl<ActivitiesBelongsDao, ActivitiesBelongs> implements ActivitiesBelongsService {
}
