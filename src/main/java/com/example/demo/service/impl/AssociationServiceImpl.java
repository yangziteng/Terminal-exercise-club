package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.AssociationDao;
import com.example.demo.domain.Association;
import com.example.demo.domain.AssociationUser;
import com.example.demo.domain.User;
import com.example.demo.dto.AssociationInfo;
import com.example.demo.service.AssociationService;
import com.example.demo.service.AssociationUserService;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociationServiceImpl extends ServiceImpl<AssociationDao, Association> implements AssociationService {
    @Autowired
    private UserService userService;
    @Autowired
    private AssociationUserService associationUserService;
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
        return associationInfo;
    }

    @Override
    public List<AssociationInfo> listByUserId(Long id) {
        User user = userService.getById(id);
        LambdaQueryWrapper<AssociationUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssociationUser::getUserId, user.getId());
        List<AssociationUser> list = associationUserService.list(wrapper);
        List<AssociationInfo> associations = list.stream().map((item)->{
            LambdaQueryWrapper<Association> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(Association::getId, item.getAssociationId());
            Association association = this.getOne(wrapper1);
            AssociationInfo associationInfo = new AssociationInfo();
            BeanUtils.copyProperties(association,associationInfo);
            associationInfo.setLeader(user);
            return associationInfo;
        }).collect(Collectors.toList());
        return associations;
    }
}
