package com.example.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssociationUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long associationId;

    private Long userId;
}
