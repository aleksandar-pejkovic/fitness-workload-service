package com.example.fitnessworkloadservice.model;

import com.example.fitnessworkloadservice.enums.MonthEnum;

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
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private MonthEnum monthEnum;

    @ManyToOne
    @JoinColumn(name = "year_id")
    private Year year;

    private int trainingDurationSum;

    public void increaseDurationSum(int trainingDuration) {
        this.trainingDurationSum += trainingDuration;
    }

    public void decreaseDurationSum(int trainingDuration) {
        this.trainingDurationSum = Math.max(0, this.getTrainingDurationSum() - trainingDuration);
    }
}
