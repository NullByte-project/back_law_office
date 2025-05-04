package com.example.back_law_office.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back_law_office.models.Procedure;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
    Optional<Procedure> findById(Long id); // Método para encontrar un procedimiento por su ID
    List<Procedure> findByAreaId(Long area); // Método para encontrar un procedimiento por el ID del área
}
