package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Activities implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long associationId;

    private Long userId;

    //申请状态(0,申请成功;1,待审核;2,面试中;3,失败)
    private Long status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
