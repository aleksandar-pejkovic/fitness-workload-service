package com.example.fitnessworkloadservice.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.fitnessworkloadservice.model.Year;

@Repository
public interface YearRepository extends ListCrudRepository<Year, Long> {

    Optional<Year> findByYear(int year);
}
