package com.example.back_law_office.dtos;
import java.time.LocalDateTime;

import com.example.back_law_office.models.DiferentialApproaches;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateClientDTO {
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotBlank(message = "El apellido es requerido")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastName;

    private String secondLastName;

    @NotBlank(message = "El tipo de documento es requerido")
    private String typeDoc;

    @NotBlank(message = "La identificación es requerida")
    private String identification;

    private LocalDateTime birthDate;

    @NotBlank(message = "El teléfono es requerido")
    private String phone;

    @NotBlank(message = "El correo electrónico es requerido")
    private String email;
    private String address;
    private String city;

    private DiferentialApproaches diferentialApproaches; // FK a DiferentialApproaches
}