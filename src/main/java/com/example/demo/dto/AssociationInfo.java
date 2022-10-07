package com.example.demo.dto;

import com.example.demo.domain.Association;
import com.example.demo.domain.User;
import lombok.Data;


@Data
public class AssociationInfo extends Association {
    private User leader;
}
