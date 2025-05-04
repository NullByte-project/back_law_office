package com.example.back_law_office.controllers;

import com.example.back_law_office.dtos.CreateAreaDTO;
import com.example.back_law_office.dtos.AreaDTO;
import com.example.back_law_office.services.AreaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
public class AreaController {

    @Autowired
    private AreaService areaService;

    // Crear un área
    @PostMapping("/create")
    public ResponseEntity<AreaDTO> createArea(@RequestBody CreateAreaDTO createAreaDTO) {
        AreaDTO areaDTO = areaService.createArea(createAreaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(areaDTO);
    }

    // Obtener todas las áreas
    @GetMapping("/all")
    public ResponseEntity<List<AreaDTO>> getAllAreas() {
        List<AreaDTO> areas = areaService.getAllAreas();
        return ResponseEntity.ok(areas);
    }

    // Obtener un área por ID
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<AreaDTO> getAreaById(@PathVariable Long id) {
        AreaDTO areaDTO = areaService.getAreaById(id);
        return ResponseEntity.ok(areaDTO);
    }

    // Actualizar un área existente
    @PutMapping("/update/{id}")
    public ResponseEntity<AreaDTO> updateArea(@PathVariable Long id, @RequestBody CreateAreaDTO createAreaDTO) {
        AreaDTO updatedArea = areaService.updateArea(id, createAreaDTO);
        return ResponseEntity.ok(updatedArea);
    }

    // Eliminar un área por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable Long id) {
        areaService.deleteArea(id);
        return ResponseEntity.noContent().build();
    }
}