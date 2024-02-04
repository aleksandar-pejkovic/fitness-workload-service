package com.example.fitnessworkinghourscalculationservice.dto;

import java.util.Date;

import com.example.fitnessworkinghourscalculationservice.enums.ActionType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrainerDTO {

    private String username;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private Date trainingDate;

    private int trainingDuration;

    private ActionType actionType;
}
