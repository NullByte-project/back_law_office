package com.example.back_law_office.dtos;

import lombok.Data;

@Data
public class StageDTO {
    private Long id;
    private String name;
    private String description;
    private Integer externalDeadLine;
}