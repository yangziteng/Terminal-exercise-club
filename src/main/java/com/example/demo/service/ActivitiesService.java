package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.Result;
import com.example.demo.domain.Activities;
import com.example.demo.dto.ActivitiesInfo;

import java.util.List;

public interface ActivitiesService extends IService<Activities> {
    public List<ActivitiesInfo> listAll();
}
