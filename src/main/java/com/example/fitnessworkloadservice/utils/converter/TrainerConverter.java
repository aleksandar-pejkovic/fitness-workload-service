package com.example.fitnessworkloadservice.utils.converter;

import com.example.fitnessworkloadservice.dto.WorkloadRequestDTO;
import com.example.fitnessworkloadservice.model.Trainer;

public class TrainerConverter {

    public static Trainer convertFromDTO(WorkloadRequestDTO workloadRequestDTO) {
        return Trainer.builder()
                .username(workloadRequestDTO.getUsername())
                .firstName(workloadRequestDTO.getFirstName())
                .lastName(workloadRequestDTO.getLastName())
                .status(workloadRequestDTO.isActive())
                .build();
    }
}
