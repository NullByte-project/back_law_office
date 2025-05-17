package com.example.back_law_office.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InterviewDTO {
    private Long id;
    private String factualDescription;
    private LocalDateTime creationDate;
    private UserDTO responsible;
    private ClientDTO client;
    private String legalConcept;
    private String action;
}