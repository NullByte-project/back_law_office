package com.example.back_law_office.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateInterviewDTO {
    private String factualDescription;
    private LocalDateTime creationDate;
    private Long responsibleId;
    private Long clientId;
    private String legalConcept;
    private String reference;
    private CreateSocioeconomicStudyDTO socioeconomicStudy;
}
