package com.example.fitnessworkloadservice.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.fitnessworkloadservice.enums.MonthEnum;
import com.example.fitnessworkloadservice.model.Month;

@Repository
public interface MonthRepository extends ListCrudRepository<Month, Long> {

    Optional<Month> findByTrainerUsernameAndYearYearAndMonthEnum(
            @Param("username") String username,
            @Param("year") int year,
            @Param("monthEnum") MonthEnum monthEnum
    );
}
