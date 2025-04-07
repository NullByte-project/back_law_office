package com.example.back_law_office.dtos;

import lombok.Data;

@Data
public class CreateSocioeconomicStudyDTO {
    private Long householdId;
    private String description;
    private String result;
    private Double personalIncome;
    private String profession;
    private Double familyIncome;
    private Integer workingHouseholdMembers;
    private String socioeconomicLevel;
    private Integer householdSize;
    private Integer financialDependents;
    private Boolean isFinancialDependent;
    private Boolean supporterPayLawyer;
    private String comments;
}