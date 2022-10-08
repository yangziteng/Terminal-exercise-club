package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Result;
import com.example.demo.dao.ActivitiesDao;
import com.example.demo.domain.Activities;
import com.example.demo.domain.Association;
import com.example.demo.domain.User;
import com.example.demo.dto.ActivitiesInfo;
import com.example.demo.service.ActivitiesService;
import com.example.demo.service.AssociationService;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivitiesServiceImpl extends ServiceImpl<ActivitiesDao, Activities> implements ActivitiesService {
    @Autowired
    private AssociationService associationService;
    @Autowired
    private UserService userService;
    @Override
    public List<ActivitiesInfo> listAll() {
        List<Activities> activities = this.list();
        List<ActivitiesInfo> activitiesInfos = activities.stream().map((item)->{
            ActivitiesInfo activitiesInfo = new ActivitiesInfo();

            BeanUtils.copyProperties(item,activitiesInfo);

            Long leaderId = item.getLeaderId();
            User user = userService.getById(leaderId);
            activitiesInfo.setLeader(user);

            Long associationId = item.getAssociationId();
            Association association = associationService.getById(associationId);
            activitiesInfo.setAssociation(association);
            return activitiesInfo;
        }).collect(Collectors.toList());
        return activitiesInfos;
    }
}
