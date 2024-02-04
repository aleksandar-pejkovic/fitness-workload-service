package com.example.fitnessworkinghourscalculationservice.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.fitnessworkinghourscalculationservice.model.Year;

@Repository
public interface YearRepository extends ListCrudRepository<Year, Long> {

    List<Year> findByTrainerUsername(String username);
}
