package com.example.back_law_office.controllers;

import com.example.back_law_office.dtos.CreateStageLegalActionDTO;
import com.example.back_law_office.dtos.StageLegalActionDTO;
import com.example.back_law_office.services.StageLegalActionService;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stages-legal-action")
public class StageLegalActionController {

    @Autowired
    private StageLegalActionService stageService;

    @PostMapping("/create")
    public ResponseEntity<StageLegalActionDTO> createStage(@RequestBody CreateStageLegalActionDTO createStageDTO) {
        StageLegalActionDTO result=  stageService.createStageLegalAction(createStageDTO);
        return ResponseEntity.status(201).body(result);
    }


    //haz un get para obtener todas las etapas de un procedimiento por su legal action id
    @GetMapping("/get-by-legal-action/{legalActionId}")
    public ResponseEntity<?> getStagesByLegalActionId(@PathVariable Long legalActionId) {
        return ResponseEntity.ok(stageService.getStagesByLegalActionId(legalActionId));
    }
}