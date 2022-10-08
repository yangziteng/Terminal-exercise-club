package com.example.demo.dto;

import com.example.demo.domain.Activities;
import com.example.demo.domain.Association;
import com.example.demo.domain.User;
import lombok.Data;


@Data
public class ActivitiesInfo extends Activities {
    private User leader;
    private Association association;
}
