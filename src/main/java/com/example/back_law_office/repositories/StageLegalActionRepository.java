package com.example.back_law_office.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back_law_office.models.StageLegalAction;
// ...existing code...
import java.util.List;

public interface StageLegalActionRepository extends JpaRepository<StageLegalAction, Long> {
    List<StageLegalAction> findByLegalActionId(Long legalActionId);
}
