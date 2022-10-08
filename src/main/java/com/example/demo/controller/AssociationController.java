package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.domain.Association;
import com.example.demo.dto.AssociationInfo;
import com.example.demo.service.AssociationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/association")
@Slf4j
public class AssociationController {
    @Autowired
    private AssociationService associationService;

    @GetMapping("/all/{status}")
    public Result<List<AssociationInfo>> list(@PathVariable Integer status) {
        List<AssociationInfo> list = associationService.listAll(status);
        return Result.success(list);
    }

    @PostMapping("/apply")
    public Result<String> apply(HttpServletRequest request, @RequestBody Association association) {
        log.info(association.toString());
        //获取当前用户id
        Long leaderId = Long.parseLong(request.getSession().getAttribute("user").toString());
        association.setLeaderId(leaderId);
        log.info(leaderId.toString());

        //根据社团名称查询数据库是否有重复
        LambdaQueryWrapper<Association> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Association::getName, association.getName());
        Association association1 = associationService.getOne(wrapper);

        //如果查到有数据则表示社团已存在
        if (association1 != null) {
            return Result.error("申请失败,社团已存在");
        }

        //数据库中没有该用户数据,注册成功
        associationService.save(association);
        return Result.success("社团申请成功,待审核");
    }

    @PutMapping
    public Result<String> update(@RequestBody Association association) {
        boolean b = associationService.updateById(association);
        return b? Result.success("社团信息更新成功"): Result.error("社团信息更新失败,未找到该社团");
    }
}
