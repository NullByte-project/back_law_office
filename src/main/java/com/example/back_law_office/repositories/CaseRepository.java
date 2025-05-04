package com.example.back_law_office.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.back_law_office.models.Case;

public interface CaseRepository extends JpaRepository<Case, Long> {
    // Custom query methods can be defined here if needed
    @Query("SELECT DISTINCT c FROM Case c JOIN c.legalActions la WHERE la.procedure.area.id = :areaId")
    List<Case> findCasesWithLegalActionInArea(@Param("areaId") Long areaId);

}
