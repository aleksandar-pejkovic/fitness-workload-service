package com.example.fitnessworkloadservice.model;

import com.example.fitnessworkloadservice.enums.MonthEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class MonthSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Enumerated(EnumType.ORDINAL)
    private MonthEnum monthEnum;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "year_id", unique = true)
    private Year year;

    private int trainingDurationSum;

    public void increaseDurationSum(int trainingDuration) {
        this.trainingDurationSum += trainingDuration;
    }

    public void decreaseDurationSum(int trainingDuration) {
        this.trainingDurationSum = Math.max(0, this.getTrainingDurationSum() - trainingDuration);
    }
}
