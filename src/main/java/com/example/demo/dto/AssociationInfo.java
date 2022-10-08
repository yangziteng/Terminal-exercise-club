package com.example.demo.dto;

import com.example.demo.domain.Association;
import com.example.demo.domain.User;
import lombok.Data;

import java.util.List;


@Data
public class AssociationInfo extends Association {
    private User leader;
    private List<String> belongs;
}
