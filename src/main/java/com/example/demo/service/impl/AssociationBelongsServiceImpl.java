package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.AssociationBelongsDao;
import com.example.demo.domain.AssociationBelongs;
import com.example.demo.service.AssociationBelongsService;
import org.springframework.stereotype.Service;

@Service
public class AssociationBelongsServiceImpl extends ServiceImpl<AssociationBelongsDao, AssociationBelongs> implements AssociationBelongsService {
}
