package com.example.fitnessworkinghourscalculationservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.fitnessworkinghourscalculationservice.dto.TrainerDTO;

@Repository
public interface TrainerRepository extends ListCrudRepository<TrainerDTO, Long> {
}
