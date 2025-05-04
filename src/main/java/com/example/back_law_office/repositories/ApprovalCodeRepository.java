package com.example.back_law_office.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back_law_office.models.ApprovalCode;

public interface ApprovalCodeRepository extends JpaRepository<ApprovalCode, Long> {
    // Custom query methods can be defined here if needed
    Optional<ApprovalCode> findByCodeAndUsed(String code, boolean used);
    
} 
