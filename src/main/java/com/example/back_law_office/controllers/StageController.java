package com.example.back_law_office.controllers;

import com.example.back_law_office.dtos.CreateStageDTO;
import com.example.back_law_office.dtos.StageDTO;
import com.example.back_law_office.services.StageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stages")
public class StageController {

    @Autowired
    private StageService stageService;

    // Crear una nueva etapa
    @PostMapping("/create")
    public ResponseEntity<StageDTO> createStage(@RequestBody CreateStageDTO createStageDTO) {
        StageDTO stageDTO = stageService.createStage(createStageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(stageDTO);
    }

    // Obtener todas las etapas
    @GetMapping("/all")
    public ResponseEntity<List<StageDTO>> getAllStages() {
        List<StageDTO> stages = stageService.getAllStages();
        return ResponseEntity.ok(stages);
    }

    // Obtener una etapa por ID
    @GetMapping("/{id}")
    public ResponseEntity<StageDTO> getStageById(@PathVariable Long id) {
        StageDTO stageDTO = stageService.getStageById(id);
        return ResponseEntity.ok(stageDTO);
    }

    // Actualizar una etapa existente
    @PutMapping("/{id}")
    public ResponseEntity<StageDTO> updateStage(@PathVariable Long id, @RequestBody CreateStageDTO createStageDTO) {
        StageDTO updatedStage = stageService.updateStage(id, createStageDTO);
        return ResponseEntity.ok(updatedStage);
    }

    // Eliminar una etapa por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        stageService.deleteStage(id);
        return ResponseEntity.noContent().build();
    }
}