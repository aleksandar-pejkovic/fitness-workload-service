package com.example.fitnessworkloadservice.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String firstName;

    private String lastName;

    private boolean status;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Year> years;
}
