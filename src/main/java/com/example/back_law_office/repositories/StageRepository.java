package com.example.back_law_office.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back_law_office.models.Stage;

public interface StageRepository extends JpaRepository<Stage, Long> {
    // Custom query methods can be defined here if needed
    Optional<Stage> findById(Long id); // Example of a custom query method to find a stage by its name
}
