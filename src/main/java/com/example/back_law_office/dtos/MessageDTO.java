package com.example.back_law_office.dtos;

import lombok.Data;

@Data
public class MessageDTO {
    private String to;
    private String subject;
    private String body;
}
