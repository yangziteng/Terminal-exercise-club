package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.AssociationDao;
import com.example.demo.domain.*;
import com.example.demo.dto.AssociationInfo;
import com.example.demo.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociationServiceImpl extends ServiceImpl<AssociationDao, Association> implements AssociationService {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivitiesService activitiesService;
    @Autowired
    private AssociationBelongsService associationBelongsService;
    @Autowired
    private ActivitiesBelongsService activitiesBelongsService;
    @Override
    public List<AssociationInfo> listAll(Integer status) {
        LambdaQueryWrapper<Association> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Association::getStatus, status);
        List<Association> associations = this.list(wrapper);
        List<AssociationInfo> associationInfos = associations.stream().map((item)->{
            AssociationInfo associationInfo = new AssociationInfo();
            BeanUtils.copyProperties(item,associationInfo);
            Long leaderId = item.getLeaderId();
            User user = userService.getById(leaderId);
            associationInfo.setLeader(user);

            LambdaQueryWrapper<AssociationBelongs> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(AssociationBelongs::getAssociationId, item.getId());
            List<AssociationBelongs> belongs = associationBelongsService.list(wrapper1);
            List<String> belongStr = belongs.stream().map((items)->{
                String s = items.getBelongs();
                return s;
            }).collect(Collectors.toList());
            associationInfo.setBelongs(belongStr);
            return associationInfo;
        }).collect(Collectors.toList());
        return associationInfos;
    }

    @Override
    public AssociationInfo listById(Long id) {
        Association association = this.getById(id);

        AssociationInfo associationInfo = new AssociationInfo();
        BeanUtils.copyProperties(association, associationInfo);

        Long leaderId = association.getLeaderId();
        User user = userService.getById(leaderId);

        associationInfo.setLeader(user);

        LambdaQueryWrapper<AssociationBelongs> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(AssociationBelongs::getAssociationId, association.getId());
        List<AssociationBelongs> belongs = associationBelongsService.list(wrapper1);
        List<String> belongStr = belongs.stream().map((items)->{
            String s = items.getBelongs();
            return s;
        }).collect(Collectors.toList());
        associationInfo.setBelongs(belongStr);
        return associationInfo;
    }

    @Override
    public List<AssociationInfo> listByUserId(Long id) {
        User user = userService.getById(id);
        LambdaQueryWrapper<Activities> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Activities::getUserId, user.getId());
        List<Activities> list = activitiesService.list(wrapper);
        List<AssociationInfo> associations = list.stream().map((item)->{
            LambdaQueryWrapper<Association> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(Association::getId, item.getAssociationId());
            Association association = this.getOne(wrapper1);
            AssociationInfo associationInfo = new AssociationInfo();
            BeanUtils.copyProperties(association,associationInfo);
            associationInfo.setLeader(user);

            LambdaQueryWrapper<ActivitiesBelongs> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(ActivitiesBelongs::getActivitiesId, item.getId());
            List<ActivitiesBelongs> belongs = activitiesBelongsService.list(wrapper2);
            List<String> belongStr = belongs.stream().map((items)->{
                String s = items.getBelongs();
                return s;
            }).collect(Collectors.toList());
            associationInfo.setBelongs(belongStr);
            return associationInfo;
        }).collect(Collectors.toList());
        return associations;
    }
}
