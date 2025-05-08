package com.example.back_law_office.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back_law_office.models.Area;

public interface AreaRepository extends JpaRepository<Area, Long> {
    // Custom query methods can be defined here if needed
    // For example, you can add methods to find areas by name or other criteria

    
} 
