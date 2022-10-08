package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Activities implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键id
    private Long id;

    //活动名
    private String name;

    //活动所属社团
    private Long associationId;

    //活动负责人
    private Long leaderId;

    //活动流程
    private String process;

    //活动描述
    private String description;

    //奖励
    private String reward;

    //活动类型
    private String type;

    //活动开始时间
    private LocalDateTime startTime;

    //活动结束时间
    private LocalDateTime endTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
