package com.example.back_law_office.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back_law_office.models.SocioEconomicStudy;

public interface SocioEconomicStudyRepository extends JpaRepository<SocioEconomicStudy, Long> {
    // Custom query methods can be defined here if needed
    // For example, you can add methods to find studies by client ID or other criteria

    
}