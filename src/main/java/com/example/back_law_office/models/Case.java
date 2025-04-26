package com.example.back_law_office.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

import com.example.back_law_office.helpers.enums.State;

import lombok.Data;

@Entity
@Data
@Table(name = "cases")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "state", nullable = false)
    private State state;

    @Column(name = "folder", nullable = false)
    private String folder;
    @OneToOne
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview; 

    @OneToMany(mappedBy = "lCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LegalAction> legalActions;

}