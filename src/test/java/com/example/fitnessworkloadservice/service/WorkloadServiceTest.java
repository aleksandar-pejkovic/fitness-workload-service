package com.example.fitnessworkloadservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.fitnessworkloadservice.dto.TrainingRequestDTO;
import com.example.fitnessworkloadservice.enums.ActionType;
import com.example.fitnessworkloadservice.enums.MonthEnum;
import com.example.fitnessworkloadservice.model.MonthSummary;
import com.example.fitnessworkloadservice.model.Trainer;
import com.example.fitnessworkloadservice.model.YearSummary;
import com.example.fitnessworkloadservice.repository.MonthRepository;
import com.example.fitnessworkloadservice.repository.TrainerRepository;
import com.example.fitnessworkloadservice.repository.YearRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WorkloadService.class})
class WorkloadServiceTest {

    @MockBean
    private MonthRepository monthRepository;

    @MockBean
    private YearRepository yearRepository;

    @MockBean
    private TrainerRepository trainerRepository;

    @Autowired
    private WorkloadService workloadService;

    @Test
    void testProcessTrainingRequestAdd() {
        // Create mock objects
        final int TRAINING_DURATION = 60;
        TrainingRequestDTO trainingRequestDTO = TrainingRequestDTO.builder()
                .username("username")
                .firstName("firstName")
                .lastName("lastName")
                .trainingDate(new Date())
                .actionType(ActionType.ADD)
                .isActive(true)
                .trainingDuration(TRAINING_DURATION)
                .build();

        Trainer trainer = mock(Trainer.class);
        YearSummary yearSummary = mock(YearSummary.class);
        MonthSummary monthSummary = mock(MonthSummary.class);

        // Set up mocks
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(yearRepository.findByTrainerAndYearValue(any(Trainer.class), anyInt())).thenReturn(Optional.of(yearSummary));
        when(monthRepository.findByYearSummaryAndMonthEnum(any(YearSummary.class), any(MonthEnum.class))).thenReturn(Optional.of(monthSummary));
        doNothing().when(monthSummary).increaseDurationSum(anyInt());

        // Call the method
        workloadService.processTrainingRequest(trainingRequestDTO);

        // Verify interactions
        verify(trainerRepository).findByUsername(anyString());
        verify(yearRepository).findByTrainerAndYearValue(any(Trainer.class), anyInt());
        verify(monthRepository).findByYearSummaryAndMonthEnum(any(YearSummary.class), any(MonthEnum.class));
        verify(monthSummary).increaseDurationSum(TRAINING_DURATION);
        verify(monthSummary, never()).decreaseDurationSum(anyInt());
        verify(monthRepository).save(monthSummary);
    }

    @Test
    void testProcessTrainingRequestDelete() {
        // Create mock objects
        final int TRAINING_DURATION = 60;
        TrainingRequestDTO trainingRequestDTO = TrainingRequestDTO.builder()
                .username("username")
                .firstName("firstName")
                .lastName("lastName")
                .trainingDate(new Date())
                .actionType(ActionType.DELETE)
                .isActive(true)
                .trainingDuration(TRAINING_DURATION)
                .build();

        Trainer trainer = mock(Trainer.class);
        YearSummary yearSummary = mock(YearSummary.class);
        MonthSummary monthSummary = mock(MonthSummary.class);

        // Set up mocks
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(yearRepository.findByTrainerAndYearValue(any(Trainer.class), anyInt())).thenReturn(Optional.of(yearSummary));
        when(monthRepository.findByYearSummaryAndMonthEnum(any(YearSummary.class), any(MonthEnum.class))).thenReturn(Optional.of(monthSummary));
        doNothing().when(monthSummary).increaseDurationSum(anyInt());

        // Call the method
        workloadService.processTrainingRequest(trainingRequestDTO);

        // Verify interactions
        verify(trainerRepository).findByUsername(anyString());
        verify(yearRepository).findByTrainerAndYearValue(any(Trainer.class), anyInt());
        verify(monthRepository).findByYearSummaryAndMonthEnum(any(YearSummary.class), any(MonthEnum.class));
        verify(monthSummary).decreaseDurationSum(TRAINING_DURATION);
        verify(monthSummary, never()).increaseDurationSum(anyInt());
        verify(monthRepository).save(monthSummary);
    }

    @Test
    void testGetWorkloadSuccess() {
        // Create mock objects
        Trainer trainer = mock(Trainer.class);
        YearSummary yearSummary = mock(YearSummary.class);
        MonthSummary monthSummary = mock(MonthSummary.class);

        // Set up mocks
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(yearRepository.findByTrainerAndYearValue(any(Trainer.class), anyInt())).thenReturn(Optional.of(yearSummary));
        when(monthRepository.findByYearSummaryAndMonthEnum(any(YearSummary.class), any(MonthEnum.class))).thenReturn(Optional.of(monthSummary));
        when(monthSummary.getTrainingDurationSum()).thenReturn(180); // 3 hours

        // Call the method
        int workload = workloadService.getWorkload("username", 2024, 2);

        // Assertions
        assertEquals(3, workload); // Verify returned workload is correct
        verify(trainerRepository).findByUsername(anyString());
        verify(yearRepository).findByTrainerAndYearValue(any(Trainer.class), anyInt());
        verify(monthRepository).findByYearSummaryAndMonthEnum(any(YearSummary.class), any(MonthEnum.class));
    }
}
