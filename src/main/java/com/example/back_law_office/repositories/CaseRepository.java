package com.example.back_law_office.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back_law_office.models.Case;

public interface CaseRepository extends JpaRepository<Case, Long> {
    // Custom query methods can be defined here if needed
    
}
