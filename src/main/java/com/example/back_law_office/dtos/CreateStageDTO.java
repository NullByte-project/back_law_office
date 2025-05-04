package com.example.back_law_office.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateStageDTO {

    private String comments;

    private LocalDate internalDeadline;

    private Long legalActionId;

    private Long stageId;
}