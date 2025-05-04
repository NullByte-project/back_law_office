package com.example.back_law_office.controllers;

import com.example.back_law_office.dtos.CreateLegalActionDTO;
import com.example.back_law_office.dtos.LegalActionDTO;
import com.example.back_law_office.services.CaseService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cases")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @PostMapping("/addlegalAction")
    public ResponseEntity<?> addLegalActionToCase(@RequestParam(name = "caseId") Long caseId, @Valid @RequestBody CreateLegalActionDTO legalActionDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        LegalActionDTO legalAction = caseService.addLegalActionToCase(caseId, legalActionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(legalAction);
    }
}