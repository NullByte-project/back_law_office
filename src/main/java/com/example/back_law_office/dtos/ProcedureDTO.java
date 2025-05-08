package com.example.back_law_office.dtos;

import lombok.Data;

@Data
public class ProcedureDTO {
    private Long id;
    private Long areaId;
    private String name;
    private String description;
}