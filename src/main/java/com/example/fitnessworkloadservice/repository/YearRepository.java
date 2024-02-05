package com.example.fitnessworkloadservice.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.fitnessworkloadservice.model.Trainer;
import com.example.fitnessworkloadservice.model.YearSummary;

@Repository
public interface YearRepository extends ListCrudRepository<YearSummary, Long> {

    Optional<YearSummary> findByTrainerAndYearValue(Trainer trainer, int yearValue);
}
