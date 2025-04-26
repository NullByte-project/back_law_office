package com.example.back_law_office.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCaseDTO {
    @NotBlank(message = "El expediente es obligatorio para la creación del caso")
    private String folder;
    private CreateLegalActionDTO legalAction;
}
