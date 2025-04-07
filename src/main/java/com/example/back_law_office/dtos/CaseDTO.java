package com.example.back_law_office.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseDTO {
    private Long id;
    private LocalDateTime creationDate;
    private String state;
    private String reference;
    private Long interviewId; // FK a Interview
}