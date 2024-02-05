package com.example.fitnessworkloadservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fitnessworkloadservice.dto.TrainingRequestDTO;
import com.example.fitnessworkloadservice.enums.ActionType;
import com.example.fitnessworkloadservice.enums.MonthEnum;
import com.example.fitnessworkloadservice.model.Month;
import com.example.fitnessworkloadservice.model.Trainer;
import com.example.fitnessworkloadservice.model.Year;
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

        Year currentYear = getOrCreateYear(trainer, trainingRequestDTO.getTrainingDate().getYear());

        Month currentMonth = getOrCreateMonth(currentYear, trainingRequestDTO.getTrainingDate().getMonth());

        if (trainingRequestDTO.getActionType() == ActionType.ADD) {
            currentMonth.increaseDurationSum(trainingRequestDTO.getTrainingDuration());
        } else if (trainingRequestDTO.getActionType() == ActionType.DELETE) {
            currentMonth.decreaseDurationSum(trainingRequestDTO.getTrainingDuration());
        }

        monthRepository.save(currentMonth);
    }

    private Year getOrCreateYear(Trainer trainer, int yearValue) {
        return yearRepository.findByTrainerAndYear(trainer, yearValue)
                .orElseGet(() -> {
                    Year newYear = Year.builder()
                            .year(yearValue)
                            .trainer(trainer)
                            .build();
                    return yearRepository.save(newYear);
                });
    }

    private Month getOrCreateMonth(Year year, int monthValue) {
        return monthRepository.findByYearAndMonthEnum(year, MonthEnum.getMonthEnum(monthValue))
                .orElseGet(() -> {
                    Month newMonth = Month.builder()
                            .year(year)
                            .monthEnum(MonthEnum.getMonthEnum(monthValue))
                            .trainingDurationSum(0)
                            .build();
                    return monthRepository.save(newMonth);
                });
    }
}
