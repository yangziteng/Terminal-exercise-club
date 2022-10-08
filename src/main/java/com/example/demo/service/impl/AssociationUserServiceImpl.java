package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.AssociationUserDao;
import com.example.demo.domain.AssociationUser;
import com.example.demo.service.AssociationUserService;
import org.springframework.stereotype.Service;

@Service
public class AssociationUserServiceImpl extends ServiceImpl<AssociationUserDao, AssociationUser> implements AssociationUserService {
}
