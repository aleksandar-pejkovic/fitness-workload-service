package com.example.fitnessworkloadservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fitnessworkloadservice.dto.TrainingRequestDTO;
import com.example.fitnessworkloadservice.dto.WorkloadRequestDTO;
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

    @PostMapping
    public ResponseEntity<String> addWorkload(@Valid @RequestBody TrainingRequestDTO trainingRequestDTO) {
        log.info("Endpoint '/api/workload' with POST mapping was called to get trainers workload");
        workloadService.processTrainingRequest(trainingRequestDTO);
        return ResponseEntity.ok("Workload processed successfully");
    }

    @GetMapping
    public int getWorkload(@Valid @RequestBody WorkloadRequestDTO workloadRequestDTO) {
        log.info("Endpoint '/api/workload' with GET mapping was called to get trainers workload");
        return workloadService.getTrainersWorkload(workloadRequestDTO);
    }
}
