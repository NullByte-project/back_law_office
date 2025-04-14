package com.example.back_law_office.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "diferential_approaches")
public class DiferentialApproaches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sex")
    private String sex;

    @Column(name = "gender_identity")
    private String genderIdentity;

    @Column(name = "ethnic_group")
    private String ethnicGroup;

    @Column(name = "disability")
    private String disability;

    @Column(name = "armed_conflict_victim")
    private boolean armedConflictVictim;
}