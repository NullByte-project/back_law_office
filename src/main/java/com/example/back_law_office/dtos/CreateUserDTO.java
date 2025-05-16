package com.example.back_law_office.dtos;

import lombok.Data;

public class CreateUserDTO {
    @Data
    public static class UserDTO {
        private String username;
        private String password;
        private String email;
        private String role;
        private String phone;
    }
}
