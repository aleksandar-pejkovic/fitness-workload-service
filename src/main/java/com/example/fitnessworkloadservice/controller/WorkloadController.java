package com.example.fitnessworkloadservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fitnessworkloadservice.dto.WorkloadRequestDTO;
import com.example.fitnessworkloadservice.enums.ActionType;
import com.example.fitnessworkloadservice.service.WorkloadService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("/api/workload")
public class WorkloadController {

    private final WorkloadService workloadService;

    @Autowired
    public WorkloadController(WorkloadService workloadService) {
        this.workloadService = workloadService;
    }

    @PutMapping
    public ResponseEntity<String> addWorkload(@Valid @RequestBody WorkloadRequestDTO workloadRequestDTO) {
        if (workloadRequestDTO.getActionType().equals(ActionType.ADD)) {

        } else if (workloadRequestDTO.getActionType().equals(ActionType.DELETE)) {

        }
        return ResponseEntity.ok("Workload processed successfully");
    }

    @GetMapping
    public ResponseEntity<Integer> getWorkload(
            @RequestParam String username,
            @RequestParam int year,
            @RequestParam int month
    ) {
        log.info("Endpoint '/api/workload' with GET mapping was called to get trainers workload");
        return ResponseEntity.ok(1);
    }
}
