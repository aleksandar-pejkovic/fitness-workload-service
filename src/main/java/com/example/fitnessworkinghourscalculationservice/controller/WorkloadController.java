package com.example.fitnessworkinghourscalculationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fitnessworkinghourscalculationservice.dto.TrainerDTO;
import com.example.fitnessworkinghourscalculationservice.service.WorkloadService;

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
    public ResponseEntity<String> addWorkload(@Valid @RequestBody TrainerDTO trainerDTO) {

        return ResponseEntity.ok("Workload processed successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> removeWorkload(@Valid @RequestBody TrainerDTO trainerDTO) {

        return ResponseEntity.ok("Workload processed successfully");
    }

    @GetMapping
    public int getWorkload(
            @RequestParam String username,
            @RequestParam int year,
            @RequestParam int month
    ) {
        log.info("Endpoint '/api/workload' with GET mapping was called to get trainers workload");
        return workloadService.getWorkingHours(username, year, month);
    }
}
