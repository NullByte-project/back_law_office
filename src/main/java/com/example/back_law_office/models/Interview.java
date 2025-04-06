package com.example.back_law_office.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "interviews")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "factual_description", nullable = false)
    private String factualDescription;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "responsible_id", nullable = false)
    private User responsible;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "legal_concept", nullable = false)
    private String legalConcept;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    @JoinColumn(name = "socioeconomic_study_id")
    private SocioEconomicStudy socioeconomicStudy;
}