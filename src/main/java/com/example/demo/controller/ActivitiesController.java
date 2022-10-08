package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.domain.Activities;
import com.example.demo.domain.Association;
import com.example.demo.dto.ActivitiesInfo;
import com.example.demo.service.ActivitiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/activities")
@Slf4j
public class ActivitiesController {
    @Autowired
    private ActivitiesService activitiesService;

    @GetMapping
    public Result<List<ActivitiesInfo>> list() {
        List<ActivitiesInfo> list = activitiesService.listAll();
        return Result.success(list);
    }

//    @PostMapping("/add")
//    public Result<String> add(HttpServletRequest request, @RequestBody ActivitiesInfo activitiesInfo) {
//        log.info(activitiesInfo.toString());
//        //获取当前用户id
//        Long leaderId = Long.parseLong(request.getSession().getAttribute("user").toString());
//        activitiesInfo.setLeaderId(leaderId);
//        log.info(leaderId.toString());
//
//        //根据社团名称查询数据库是否有重复
//        LambdaQueryWrapper<Association> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Association::getName, activitiesInfo.getName());
//        Association association1 = activitiesService.getOne(wrapper);
//
//        //如果查到有数据则表示社团已存在
//        if (association1 != null) {
//            return Result.error("申请失败,社团已存在");
//        }
//
//        //数据库中没有该用户数据,注册成功
//        activitiesService.save(activitiesInfo);
//        return Result.success("社团申请成功,待审核");
//    }

    @PutMapping
    public Result<String> update(@RequestBody Activities activities) {
        boolean b = activitiesService.updateById(activities);
        return b? Result.success("活动信息更新成功"): Result.error("活动信息更新失败,未找到该活动");
    }
}
