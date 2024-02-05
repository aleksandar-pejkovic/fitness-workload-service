package com.example.fitnessworkloadservice.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.fitnessworkloadservice.enums.MonthEnum;
import com.example.fitnessworkloadservice.model.Month;
import com.example.fitnessworkloadservice.model.Year;

@Repository
public interface MonthRepository extends ListCrudRepository<Month, Long> {

    Optional<Month> findByYearAndMonthEnum(Year year, MonthEnum monthEnum);
}
