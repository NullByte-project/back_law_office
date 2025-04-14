package com.example.back_law_office.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSocioeconomicStudyDTO {
    @NotBlank(message = "El tipo de vivienda es obligatorio")
    private String householdType;
    @NotBlank(message = "La descripción es obligatoria")
    private String description;
    @NotBlank(message = "El lugar de residencia es obligatorio")
    private String result;
    @NotNull(message = "El ingreso personal es obligatorio")
    private Double personalIncome;
    @NotBlank(message = "La profesión es obligatoria")
    private String profession;
    @NotNull(message = "El ingreso familiar es obligatorio")
    private Double familyIncome;
    @NotNull(message = "El número de miembros que trabajan en el hogar es obligatorio")
    private Integer workingHouseholdMembers;
    @NotBlank(message = "El nivel socioeconómico es obligatorio")
    private String socioeconomicLevel;
    @NotNull(message = "El tamaño del hogar es obligatorio")
    private Integer householdSize;
    @NotNull(message = "El número de dependientes financieros es obligatorio")
    private Integer financialDependents;
    @NotNull(message = "El campo 'es dependiente financiero' es obligatorio")
    private Boolean isFinancialDependent;
    @NotNull(message = "El campo 'el que apoya paga al abogado' es obligatorio")
    private Boolean supporterPayLawyer;
    @NotBlank(message = "Los comentarios son obligatorios")
    private String comments;
}