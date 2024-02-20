package com.example.fitnessworkloadservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fitnessworkloadservice.dto.TrainingRequestDTO;
import com.example.fitnessworkloadservice.service.WorkloadService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/workload")
public class WorkloadController {

    private final WorkloadService workloadService;

    @Autowired
    public WorkloadController(WorkloadService workloadService) {
        this.workloadService = workloadService;
    }

    @PostMapping
    public ResponseEntity<String> processWorkload(@Valid @RequestBody TrainingRequestDTO trainingRequestDTO) {
        log.info("Endpoint '/api/workload' with POST mapping was called to update trainers workload");
        workloadService.processTrainingRequest(trainingRequestDTO);
        return ResponseEntity.ok("Workload processed successfully");
    }

    @GetMapping
    public int getWorkload(
            @RequestParam String username,
            @RequestParam int year,
            @RequestParam int month
    ) {
        log.info("Endpoint '/api/workload' with GET mapping was called to get trainers workload");
        return workloadService.getWorkload(username, year, month);
    }
}
