package com.example.back_law_office.dtos;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String phone;
}