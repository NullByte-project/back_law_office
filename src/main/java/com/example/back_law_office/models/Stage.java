package com.example.back_law_office.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stages")
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "external_deadline", nullable = true)
    private Integer externalDeadLine;
}