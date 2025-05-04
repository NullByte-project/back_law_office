package com.example.back_law_office.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ListCasesDTO {
    private Long id;
    private LocalDateTime creationDate;
    private String state;
    private String folder;
}
