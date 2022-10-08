package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.ActivitiesDao;
import com.example.demo.domain.Activities;
import com.example.demo.service.ActivitiesService;
import org.springframework.stereotype.Service;

@Service
public class ActivitiesServiceImpl extends ServiceImpl<ActivitiesDao, Activities> implements ActivitiesService {
}
