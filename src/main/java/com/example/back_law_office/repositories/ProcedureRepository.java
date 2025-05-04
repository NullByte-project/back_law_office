package com.example.back_law_office.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back_law_office.models.Procedure;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
    
}
