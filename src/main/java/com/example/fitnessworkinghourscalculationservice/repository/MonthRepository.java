package com.example.fitnessworkinghourscalculationservice.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.fitnessworkinghourscalculationservice.model.Month;

@Repository
public interface MonthRepository extends ListCrudRepository<Month, Long> {

    List<Month> findByYearTrainerUsernameAndYearYearAndMonth(String trainerUsername, int yearValue, int monthValue);
}
