package com.example.back_law_office.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "socioeconomic_studies")
public class SocioEconomicStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "household_type")
    private String household;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "personal_income" )
    private Double personalIncome;

    @Column(name = "profession")
    private String profession;

    @Column(name = "family_income")
    private Double familyIncome;

    @Column(name = "working_household_members")
    private Integer workingHouseholdMembers;

    @Column(name = "socioeconomic_level")
    private String socioeconomicLevel;

    @Column(name = "household_size")
    private Integer householdSize;

    @Column(name = "financial_dependents")
    private Integer financialDependents;

    @Column(name = "is_financial_dependent")
    private Boolean isFinancialDependent;

    @Column(name = "supporter_pay_lawyer")
    private Boolean supporterPayLawyer;

    @Column(name = "comments")
    private String comments;
}