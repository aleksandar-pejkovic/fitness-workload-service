package com.example.fitnessworkloadservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fitnessworkloadservice.dto.TrainingRequestDTO;
import com.example.fitnessworkloadservice.enums.ActionType;
import com.example.fitnessworkloadservice.enums.MonthEnum;
import com.example.fitnessworkloadservice.model.MonthSummary;
import com.example.fitnessworkloadservice.model.Trainer;
import com.example.fitnessworkloadservice.model.YearSummary;
import com.example.fitnessworkloadservice.repository.MonthRepository;
import com.example.fitnessworkloadservice.repository.TrainerRepository;
import com.example.fitnessworkloadservice.repository.YearRepository;

@Service
public class WorkloadService {

    private final MonthRepository monthRepository;

    private final YearRepository yearRepository;

    private final TrainerRepository trainerRepository;

    public WorkloadService(MonthRepository monthRepository, YearRepository yearRepository, TrainerRepository trainerRepository) {
        this.monthRepository = monthRepository;
        this.yearRepository = yearRepository;
        this.trainerRepository = trainerRepository;
    }

    @Transactional
    public void processTrainingRequest(TrainingRequestDTO trainingRequestDTO) {
        Trainer trainer = trainerRepository.findByUsername(trainingRequestDTO.getUsername()).orElseThrow();

        YearSummary currentYearSummary = getOrCreateYear(trainer, trainingRequestDTO.getTrainingDate().getYear());

        MonthSummary currentMonthSummary = getOrCreateMonth(currentYearSummary, trainingRequestDTO.getTrainingDate().getMonth());

        if (trainingRequestDTO.getActionType() == ActionType.ADD) {
            currentMonthSummary.increaseDurationSum(trainingRequestDTO.getTrainingDuration());
        } else if (trainingRequestDTO.getActionType() == ActionType.DELETE) {
            currentMonthSummary.decreaseDurationSum(trainingRequestDTO.getTrainingDuration());
        }

        monthRepository.save(currentMonthSummary);
    }

    private YearSummary getOrCreateYear(Trainer trainer, int yearValue) {
        return yearRepository.findByTrainerAndYearValue(trainer, yearValue)
                .orElseGet(() -> {
                    YearSummary newYearSummary = YearSummary.builder()
                            .yearValue(yearValue)
                            .trainer(trainer)
                            .build();
                    return yearRepository.save(newYearSummary);
                });
    }

    private MonthSummary getOrCreateMonth(YearSummary yearSummary, int monthValue) {
        return monthRepository.findByYearSummaryAndMonthEnum(yearSummary, MonthEnum.getMonthEnum(monthValue))
                .orElseGet(() -> {
                    MonthSummary newMonthSummary = MonthSummary.builder()
                            .yearSummary(yearSummary)
                            .monthEnum(MonthEnum.getMonthEnum(monthValue))
                            .trainingDurationSum(0)
                            .build();
                    return monthRepository.save(newMonthSummary);
                });
    }
}
