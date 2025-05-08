package com.example.back_law_office.controllers;

import com.example.back_law_office.dtos.CreateProcedureDTO;
import com.example.back_law_office.dtos.ProcedureDTO;
import com.example.back_law_office.services.ProcedureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procedures")
public class ProcedureController {

    @Autowired
    private ProcedureService procedureService;

    // Crear un nuevo procedimiento
    @PostMapping
    public ResponseEntity<ProcedureDTO> createProcedure(@RequestBody CreateProcedureDTO createProcedureDTO) {
        ProcedureDTO procedureDTO = procedureService.createProcedure(createProcedureDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(procedureDTO);
    }

    // Obtener todos los procedimientos
    @GetMapping
    public ResponseEntity<List<ProcedureDTO>> getAllProcedures() {
        List<ProcedureDTO> procedures = procedureService.getAllProcedures();
        return ResponseEntity.ok(procedures);
    }

    // Obtener un procedimiento por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProcedureDTO> getProcedureById(@PathVariable Long id) {
        ProcedureDTO procedureDTO = procedureService.getProcedureById(id);
        return ResponseEntity.ok(procedureDTO);
    }

    @GetMapping("/get-by-area/{areaId}")
    public ResponseEntity<List<ProcedureDTO>> getProcedureByAreaId(@PathVariable(name= "areaId") Long areaId) {
        List<ProcedureDTO> results = procedureService.getProcedureByAreaId(areaId);
        return ResponseEntity.ok(results);
    }

    // Actualizar un procedimiento existente
    @PutMapping("/update/{id}")
    public ResponseEntity<ProcedureDTO> updateProcedure(@PathVariable Long id, @RequestBody CreateProcedureDTO createProcedureDTO) {
        ProcedureDTO updatedProcedure = procedureService.updateProcedure(id, createProcedureDTO);
        return ResponseEntity.ok(updatedProcedure);
    }

    // Eliminar un procedimiento por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProcedure(@PathVariable Long id) {
        procedureService.deleteProcedure(id);
        return ResponseEntity.noContent().build();
    }
}