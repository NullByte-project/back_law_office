package com.example.back_law_office.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "approval_codes")
public class ApprovalCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private Boolean used;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "case_id", nullable = true)
    private LegalAction legalAction;

    
}