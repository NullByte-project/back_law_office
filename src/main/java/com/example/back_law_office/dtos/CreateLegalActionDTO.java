package com.example.back_law_office.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateLegalActionDTO {
    private Long LCase;
    @NotBlank(message = "El codigo de aprobación es obligatorio para la creación del caso")
    private String approvalCode;
    private Long procedure;
    private String instructions;
    private String additionalInfo; // JSON string or similar format to store additional info

}
