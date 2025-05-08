package com.example.back_law_office.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInterviewDTO {
    @NotBlank(message = "La descripción fáctica es obligatoria")
    private String factualDescription;
    @NotNull(message = "El responsable es obligatorio")
    private Long responsibleId;
    @NotBlank(message = "El concepto jurídico es obligatorio")
    private String legalConcept;
    @NotBlank(message = "La acción a tomar es obligatoria")
    private String action;
    private CreateCaseDTO legalCase;
    private CreateClientDTO client; 
}
