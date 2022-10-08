package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AssociationBelongs implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long associationId;

    private String belongs;
}
