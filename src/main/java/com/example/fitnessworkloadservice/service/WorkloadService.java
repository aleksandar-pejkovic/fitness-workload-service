package com.example.fitnessworkloadservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fitnessworkloadservice.dto.TrainingRequestDTO;
import com.example.fitnessworkloadservice.enums.MonthEnum;
import com.example.fitnessworkloadservice.model.Month;
import com.example.fitnessworkloadservice.model.Trainer;
import com.example.fitnessworkloadservice.model.Year;
import com.example.fitnessworkloadservice.repository.MonthRepository;
import com.example.fitnessworkloadservice.repository.YearRepository;

@Service
public class WorkloadService {

    private final MonthRepository monthRepository;

    private final YearRepository yearRepository;

    public WorkloadService(MonthRepository monthRepository, YearRepository yearRepository) {
        this.monthRepository = monthRepository;
        this.yearRepository = yearRepository;
    }

    @Transactional
    public void increaseTrainingDuration(TrainingRequestDTO trainingRequestDTO) {
        Month month = getOrCreateMonthSummary(trainingRequestDTO);
        month.increaseDurationSum(trainingRequestDTO.getTrainingDuration());
        monthRepository.save(month);
    }

    @Transactional
    public void decreaseTrainingDuration(TrainingRequestDTO trainingRequestDTO) {
        Month month = getOrCreateMonthSummary(trainingRequestDTO);
        month.decreaseDurationSum(trainingRequestDTO.getTrainingDuration());
        monthRepository.save(month);
    }

    private Month getOrCreateMonthSummary(TrainingRequestDTO trainingRequestDTO) {
        return monthRepository.findByTrainerUsernameAndYearYearAndMonthEnum(
                        trainingRequestDTO.getUsername(),
                        trainingRequestDTO.getTrainingDate().getYear() + 1900,
                        MonthEnum.getMonthEnum(trainingRequestDTO.getTrainingDate().getMonth() + 1)
                )
                .orElseGet(() -> createMonthSummary(trainingRequestDTO));
    }

    private Month createMonthSummary(TrainingRequestDTO trainingRequestDTO) {
        Trainer trainer = Trainer.builder()
                .username(trainingRequestDTO.getUsername())
                .firstName(trainingRequestDTO.getFirstName())
                .lastName(trainingRequestDTO.getLastName())
                .status(trainingRequestDTO.isActive())
                .build();

        int yearValue = trainingRequestDTO.getTrainingDate().getYear() + 1900;

        Year yearEntity = yearRepository.findByYear(yearValue)
                .orElse(Year.builder()
                        .year(yearValue)
                        .build());

        Month month = Month.builder()
                .year(yearEntity)
                .monthEnum(MonthEnum.getMonthEnum(trainingRequestDTO.getTrainingDate().getMonth() + 1))
                .trainingDurationSum(0)
                .build();

        return monthRepository.save(month);
    }

}
