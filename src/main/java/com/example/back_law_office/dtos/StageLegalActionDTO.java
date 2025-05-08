package com.example.back_law_office.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class StageLegalActionDTO {
    private Long id;

    private String comments;

    private LocalDate internalDeadline;

    private StageDTO stage;
}
