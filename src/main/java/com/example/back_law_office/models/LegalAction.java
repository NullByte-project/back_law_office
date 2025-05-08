package com.example.back_law_office.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "legal_actions")
@Data 
public class LegalAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "procedure_id", nullable = false)
    private Procedure procedure;

    @Column(name = "instructions", nullable = false, length = 500)
    private String instructions;

    @Column(name = "additional_info", columnDefinition = "TEXT") 
    private String additionalInfo;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private Case lCase;

    @OneToMany(mappedBy = "legalAction")
    private List<StageLegalAction> stages;

}