package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.Association;
import com.example.demo.dto.AssociationInfo;

import java.util.List;

public interface AssociationService extends IService<Association> {
    public List<AssociationInfo> listAll(Integer status);
    public AssociationInfo listById(Long id);
    public List<AssociationInfo> listByUserId(Long id);
}
