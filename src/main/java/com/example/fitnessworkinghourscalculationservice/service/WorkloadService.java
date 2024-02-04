package com.example.fitnessworkinghourscalculationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fitnessworkinghourscalculationservice.model.Month;
import com.example.fitnessworkinghourscalculationservice.repository.MonthRepository;

@Service
public class WorkloadService {

    private static final int MINUTES_IN_HOUR = 60;

    private final MonthRepository monthRepository;

    @Autowired
    public WorkloadService(MonthRepository monthRepository) {
        this.monthRepository = monthRepository;
    }

    public int getWorkingHours(String trainerUsername, int year, int month) {
        List<Month> trainersMonths =
                monthRepository.findByYearTrainerUsernameAndYearYearAndMonth(trainerUsername, year, month);
        int workingMinutes = trainersMonths.stream()
                .mapToInt(Month::getTrainingSummaryDuration)
                .sum();
        return workingMinutes / MINUTES_IN_HOUR;
    }
}
