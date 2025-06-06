package com.example.back_law_office.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaseDTO {
    private Long id;
    private LocalDateTime creationDate;
    private String state;
    private String folder;
    private InterviewDTO interview; 
    private List<LegalActionDTO> legalActions;
}