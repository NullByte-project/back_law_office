package com.example.back_law_office.services;

import com.example.back_law_office.dtos.CreateProcedureDTO;
import com.example.back_law_office.dtos.ProcedureDTO;
import com.example.back_law_office.models.Area;
import com.example.back_law_office.models.Procedure;
import com.example.back_law_office.repositories.AreaRepository;
import com.example.back_law_office.repositories.ProcedureRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Crear un nuevo procedimiento
    public ProcedureDTO createProcedure(CreateProcedureDTO createProcedureDTO) {
        Area area = areaRepository.findById(createProcedureDTO.getAreaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid area ID"));

        Procedure procedure = modelMapper.map(createProcedureDTO, Procedure.class);
        procedure.setArea(area);

        Procedure savedProcedure = procedureRepository.save(procedure);
        return modelMapper.map(savedProcedure, ProcedureDTO.class);
    }

    // Obtener todos los procedimientos
    public List<ProcedureDTO> getAllProcedures() {
        List<Procedure> procedures = procedureRepository.findAll();
        return procedures.stream()
                .map(procedure -> modelMapper.map(procedure, ProcedureDTO.class))
                .collect(Collectors.toList());
    }

    // Obtener un procedimiento por ID
    public ProcedureDTO getProcedureById(Long id) {
        Procedure procedure = procedureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Procedure not found"));
        return modelMapper.map(procedure, ProcedureDTO.class);
    }

    public List<ProcedureDTO> getProcedureByAreaId(Long id) {
        List<Procedure> procedures = procedureRepository.findByAreaId(id);
        return procedures.stream()
        .map(procedure -> modelMapper.map(procedure, ProcedureDTO.class))
        .collect(Collectors.toList());
    }

    // Actualizar un procedimiento existente
    public ProcedureDTO updateProcedure(Long id, CreateProcedureDTO createProcedureDTO) {
        Procedure existingProcedure = procedureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Procedure not found"));

        Area area = areaRepository.findById(createProcedureDTO.getAreaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid area ID"));

        modelMapper.map(createProcedureDTO, existingProcedure);
        existingProcedure.setArea(area);

        Procedure updatedProcedure = procedureRepository.save(existingProcedure);
        return modelMapper.map(updatedProcedure, ProcedureDTO.class);
    }

    // Eliminar un procedimiento por ID
    public void deleteProcedure(Long id) {
        if (!procedureRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Procedure not found");
        }
        procedureRepository.deleteById(id);
    }
}