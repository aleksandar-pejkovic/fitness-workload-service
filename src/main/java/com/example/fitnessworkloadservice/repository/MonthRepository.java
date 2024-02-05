package com.example.fitnessworkloadservice.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.fitnessworkloadservice.enums.MonthEnum;
import com.example.fitnessworkloadservice.model.MonthSummary;
import com.example.fitnessworkloadservice.model.YearSummary;

@Repository
public interface MonthRepository extends ListCrudRepository<MonthSummary, Long> {

    Optional<MonthSummary> findByYearSummaryAndMonthEnum(YearSummary yearSummary, MonthEnum monthEnum);
}
