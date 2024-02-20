package com.example.fitnessworkloadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FitnessWorkloadServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitnessWorkloadServiceApplication.class, args);
    }

}
