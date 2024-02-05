package com.example.fitnessworkloadservice.dto;

import java.util.Date;

import com.example.fitnessworkloadservice.enums.ActionType;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkloadRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private boolean isActive;

    @NotNull
    private Date trainingDate;

    private int trainingDuration;

    @NotNull
    private ActionType actionType;
}
