package com.example.back_law_office.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Data
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "second_lastname")
    private String secondLastName;

    @Column(name = "type_doc", nullable = false)
    private String typeDoc;

    @Column(name = "identification", nullable = false, unique = true)
    private String identification;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "diferential_approaches_id", referencedColumnName = "id")
    private DiferentialApproaches diferentialApproaches;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "socioeconomic_study_id", referencedColumnName = "id")
    private SocioEconomicStudy socioeconomicStudy;

}