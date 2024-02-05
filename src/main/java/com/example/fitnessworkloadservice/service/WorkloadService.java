package com.example.fitnessworkloadservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fitnessworkloadservice.dto.WorkloadRequestDTO;
import com.example.fitnessworkloadservice.enums.MonthEnum;
import com.example.fitnessworkloadservice.model.MonthSummary;
import com.example.fitnessworkloadservice.model.Trainer;
import com.example.fitnessworkloadservice.model.Year;
import com.example.fitnessworkloadservice.repository.MonthSummaryRepository;
import com.example.fitnessworkloadservice.repository.YearRepository;

@Service
public class WorkloadService {

    private final MonthSummaryRepository monthSummaryRepository;

    private final YearRepository yearRepository;

    public WorkloadService(MonthSummaryRepository monthSummaryRepository, YearRepository yearRepository) {
        this.monthSummaryRepository = monthSummaryRepository;
        this.yearRepository = yearRepository;
    }

    @Transactional
    public void increaseTrainingDuration(WorkloadRequestDTO workloadRequestDTO) {
        MonthSummary monthSummary = getOrCreateMonthSummary(workloadRequestDTO);
        monthSummary.increaseDurationSum(workloadRequestDTO.getTrainingDuration());
        monthSummaryRepository.save(monthSummary);
    }

    @Transactional
    public void decreaseTrainingDuration(WorkloadRequestDTO workloadRequestDTO) {
        MonthSummary monthSummary = getOrCreateMonthSummary(workloadRequestDTO);
        monthSummary.decreaseDurationSum(workloadRequestDTO.getTrainingDuration());
        monthSummaryRepository.save(monthSummary);
    }

    private MonthSummary getOrCreateMonthSummary(WorkloadRequestDTO workloadRequestDTO) {
        return monthSummaryRepository.findByTrainerUsernameAndYearYearAndMonthEnum(
                        workloadRequestDTO.getUsername(),
                        workloadRequestDTO.getTrainingDate().getYear() + 1900,
                        MonthEnum.getMonthEnum(workloadRequestDTO.getTrainingDate().getMonth() + 1)
                )
                .orElseGet(() -> createMonthSummary(workloadRequestDTO));
    }

    private MonthSummary createMonthSummary(WorkloadRequestDTO workloadRequestDTO) {
        Trainer trainer = Trainer.builder()
                .username(workloadRequestDTO.getUsername())
                .firstName(workloadRequestDTO.getFirstName())
                .lastName(workloadRequestDTO.getLastName())
                .status(workloadRequestDTO.isActive())
                .build();

        int yearValue = workloadRequestDTO.getTrainingDate().getYear() + 1900;

        Year yearEntity = yearRepository.findByYear(yearValue)
                .orElse(Year.builder()
                        .year(yearValue)
                        .build());

        MonthSummary monthSummary = MonthSummary.builder()
                .trainer(trainer)
                .year(yearEntity)
                .monthEnum(MonthEnum.getMonthEnum(workloadRequestDTO.getTrainingDate().getMonth() + 1))
                .trainingDurationSum(0)
                .build();

        return monthSummaryRepository.save(monthSummary);
    }

}
