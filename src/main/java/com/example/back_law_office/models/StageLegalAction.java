package com.example.back_law_office.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "stages_legal_actions")
public class StageLegalAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String comments;

    @Column(name = "internal_deadline", nullable = false)
    private LocalDate internalDeadline;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "legal_action_id", nullable = false)
    private LegalAction legalAction;

    @ManyToOne
    @JoinColumn(name= "stage_id", nullable = false)
    private Stage stage;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
}