package com.example.fitnessworkinghourscalculationservice.model;

import com.example.fitnessworkinghourscalculationservice.enums.MonthEnum;

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
    private long id;

    @Enumerated(EnumType.ORDINAL)
    private MonthEnum month;

    @ManyToOne
    @JoinColumn(name = "year_id")
    private Year year;

    private int trainingSummaryDuration;
}
