package com.example.back_law_office.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "procedures") // Nombre de la tabla en la base de datos
@Data // Lombok: Genera automáticamente getters, setters, equals, hashCode y toString
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long id;

    @ManyToOne
    @JoinColumn(name = "area_id", nullable = false) // Relación con el área
    private Area area;

    @Column(name = "name", nullable = false, length = 100) // Nombre del procedimiento
    private String name;

    @Column(name = "description", columnDefinition = "TEXT") // Descripción del procedimiento
    private String description;

    // Relación con preguntas adicionales (descomentada si es necesaria)
    // @OneToMany(mappedBy = "procedure", cascade = CascadeType.ALL, orphanRemoval = true)
    // private Set<AditionalQuestion> additionalQuestions;
}