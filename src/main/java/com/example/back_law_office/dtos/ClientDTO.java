package com.example.back_law_office.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String name;
    private String lastName;
    private String secondLastName;
    private String typeDoc;
    private String identification;
    private LocalDateTime birthDate;
    private String phone;
    private String email;
    private String address;
    private String city;
    private CreateSocioeconomicStudyDTO socioeconomicStudy; 
    private DiferencialAproachesDTO diferentialApproaches;
}
