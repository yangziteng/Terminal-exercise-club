package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.domain.Association;
import com.example.demo.domain.AssociationBelongs;
import com.example.demo.dto.AssociationInfo;
import com.example.demo.service.AssociationBelongsService;
import com.example.demo.service.AssociationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/association")
@Slf4j
public class AssociationController {
    @Autowired
    private AssociationService associationService;
    @Autowired
    private AssociationBelongsService associationBelongsService;

    @GetMapping("/all/{status}")
    public Result<List<AssociationInfo>> list(@PathVariable Integer status) {
        List<AssociationInfo> list = associationService.listAll(status);
        return Result.success(list);
    }

    @GetMapping("/{id}")
    public Result<AssociationInfo> listById(@PathVariable Long id) {
        AssociationInfo list = associationService.listById(id);
        return Result.success(list);
    }

    @GetMapping("/user/{id}")
    public Result<List<AssociationInfo>> listByUserId(@PathVariable Long id) {
        List<AssociationInfo> list = associationService.listByUserId(id);
        return Result.success(list);
    }

    @PostMapping("/apply")
    public Result<String> apply(HttpServletRequest request, @RequestBody AssociationInfo association) {
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

        List<String> belongs = association.getBelongs();
        LambdaQueryWrapper<AssociationBelongs> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(AssociationBelongs::getAssociationId, association.getId());
        associationBelongsService.remove(wrapper1);
        belongs.stream().map((item)->{
            AssociationBelongs associationBelongs = new AssociationBelongs();
            associationBelongs.setBelongs(item);
            associationBelongs.setAssociationId(association.getId());
            associationBelongsService.save(associationBelongs);
            return item;
        }).collect(Collectors.toList());
        return Result.success("社团申请成功,待审核");
    }

    @PutMapping
    public Result<String> update(@RequestBody AssociationInfo association) {
        boolean b = associationService.updateById(association);
        List<String> belongs = association.getBelongs();
        if (belongs != null){
            LambdaQueryWrapper<AssociationBelongs> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AssociationBelongs::getAssociationId, association.getId());
            associationBelongsService.remove(wrapper);
            belongs.stream().map((item)->{
                AssociationBelongs associationBelongs = new AssociationBelongs();
                associationBelongs.setBelongs(item);
                associationBelongs.setAssociationId(association.getId());
                associationBelongsService.save(associationBelongs);
                return item;
            }).collect(Collectors.toList());
        }
        return b? Result.success("社团信息更新成功"): Result.error("社团信息更新失败,未找到该社团");
    }
}
