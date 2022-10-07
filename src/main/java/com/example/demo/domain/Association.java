package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Association implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键id
    private Long id;

    //社团名
    private String name;

    //社团负责人
    private Long leaderId;

    //社团logo
    private String logo;

    //社团描述
    private String description;

    //进入社团需求
    private String requirement;

    //社团状态(0,正常;1,待审批;2,已销毁)
    private Long status;

    //社团类型
    private String type;

    //所属部门
    private String belongs;

    //所属学院
    private String academic;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
