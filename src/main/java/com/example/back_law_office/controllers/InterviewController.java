package com.example.back_law_office.controllers;

import com.example.back_law_office.dtos.CreateInterviewDTO;
import com.example.back_law_office.dtos.InterviewDTO;
import com.example.back_law_office.services.InterviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @PostMapping("/create")
    public ResponseEntity<?> createInterview(@Valid @RequestBody CreateInterviewDTO createInterviewDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

    
        InterviewDTO createdInterview = interviewService.createInterview(createInterviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInterview);
    }
}