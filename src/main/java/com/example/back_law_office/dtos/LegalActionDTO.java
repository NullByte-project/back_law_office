package com.example.back_law_office.dtos;

import lombok.Data;

@Data
public class LegalActionDTO {
    private Long id;
    private String instructions;
    private String additionalInfo;
    private String state; 
    private ProcedureDTO procedure;
}
