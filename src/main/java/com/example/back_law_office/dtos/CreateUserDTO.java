package com.example.back_law_office.dtos;

import lombok.Data;
@Data
public class CreateUserDTO {
    private String username;
    private String password;
    private String email;
    private String role;
    private String phone;
}
