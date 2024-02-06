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

    public static final int MINUTES_IN_HOUR = 60;
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
        Trainer trainer = getOrCreateTrainer(trainingRequestDTO);

        YearSummary currentYearSummary = getOrCreateYear(trainer, trainingRequestDTO.getTrainingDate().getYear());

        MonthSummary currentMonthSummary = getOrCreateMonth(currentYearSummary, trainingRequestDTO.getTrainingDate().getMonth());

        if (trainingRequestDTO.getActionType() == ActionType.ADD) {
            currentMonthSummary.increaseDurationSum(trainingRequestDTO.getTrainingDuration());
        } else if (trainingRequestDTO.getActionType() == ActionType.DELETE) {
            currentMonthSummary.decreaseDurationSum(trainingRequestDTO.getTrainingDuration());
        }

        monthRepository.save(currentMonthSummary);
    }

    @Transactional(readOnly = true)
    public int getWorkload(String username, int year, int month) {
        Trainer trainer = trainerRepository.findByUsername(username).orElseThrow();
        YearSummary yearSummary = yearRepository.findByTrainerAndYearValue(trainer, year).orElseThrow();
        MonthSummary monthSummary = monthRepository.findByYearSummaryAndMonthEnum(yearSummary,
                MonthEnum.getMonthEnum(month)).orElseThrow();
        return monthSummary.getTrainingDurationSum() / MINUTES_IN_HOUR;
    }

    private Trainer getOrCreateTrainer(TrainingRequestDTO trainingRequestDTO) {
        return trainerRepository.findByUsername(trainingRequestDTO.getUsername())
                .orElseGet(() -> {
                    Trainer trainer = Trainer.builder()
                            .username(trainingRequestDTO.getUsername())
                            .firstName(trainingRequestDTO.getFirstName())
                            .lastName(trainingRequestDTO.getLastName())
                            .status(trainingRequestDTO.isActive())
                            .build();
                    return trainerRepository.save(trainer);
                });
    }

    private YearSummary getOrCreateYear(Trainer trainer, int yearValue) {
        int adjustedYear = yearValue + 1900;
        return yearRepository.findByTrainerAndYearValue(trainer, adjustedYear)
                .orElseGet(() -> {
                    YearSummary newYearSummary = YearSummary.builder()
                            .yearValue(adjustedYear)
                            .trainer(trainer)
                            .build();
                    return yearRepository.save(newYearSummary);
                });
    }

    private MonthSummary getOrCreateMonth(YearSummary yearSummary, int monthValue) {
        int adjustedMonth = monthValue + 1;
        return monthRepository.findByYearSummaryAndMonthEnum(yearSummary, MonthEnum.getMonthEnum(adjustedMonth))
                .orElseGet(() -> {
                    MonthSummary newMonthSummary = MonthSummary.builder()
                            .yearSummary(yearSummary)
                            .monthEnum(MonthEnum.getMonthEnum(adjustedMonth))
                            .trainingDurationSum(0)
                            .build();
                    return monthRepository.save(newMonthSummary);
                });
    }
}
