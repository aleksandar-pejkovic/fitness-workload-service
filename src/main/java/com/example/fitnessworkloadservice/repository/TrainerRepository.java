package com.example.fitnessworkloadservice.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.fitnessworkloadservice.model.Trainer;

@Repository
public interface TrainerRepository extends ListCrudRepository<Trainer, Long> {

    Optional<Trainer> findByUsername(String username);
}
