package com.example.fitnessworkloadservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkloadRequestDTO {

    @NotNull
    private String username;

    private int yearValue;

    private int monthValue;
}
