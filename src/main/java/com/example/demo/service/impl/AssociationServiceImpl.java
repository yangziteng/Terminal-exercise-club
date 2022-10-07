package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.AssociationDao;
import com.example.demo.domain.Association;
import com.example.demo.service.AssociationService;
import org.springframework.stereotype.Service;

@Service
public class AssociationServiceImpl extends ServiceImpl<AssociationDao, Association> implements AssociationService {
}
