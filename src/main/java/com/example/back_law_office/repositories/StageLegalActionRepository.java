package com.example.back_law_office.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back_law_office.models.StageLegalAction;

public interface StageLegalActionRepository extends JpaRepository<StageLegalAction, Long> {
    // Custom query methods can be defined here if needed
    
}
