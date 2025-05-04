package com.example.back_law_office.models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "areas")
@Data 
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100) 
    private String name;

    @ManyToOne
    @JoinColumn(name = "assistant_id", nullable = true) 
    private User assistant;

    // Relación con preguntas adicionales (descomentar si es necesario)
    // @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<AditionalQuestion> additionalQuestions;
}