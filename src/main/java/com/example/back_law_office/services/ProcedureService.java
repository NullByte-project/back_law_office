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

    /**
     * Crea un nuevo procedimiento.
     * @param createProcedureDTO DTO con los datos del procedimiento a crear.
     * @return El procedimiento creado.
     * @throws ResponseStatusException si el área no es válida.
     */
    public ProcedureDTO createProcedure(CreateProcedureDTO createProcedureDTO) {
        Area area = areaRepository.findById(createProcedureDTO.getAreaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid area ID"));

        Procedure procedure = modelMapper.map(createProcedureDTO, Procedure.class);
        procedure.setArea(area);

        Procedure savedProcedure = procedureRepository.save(procedure);
        return modelMapper.map(savedProcedure, ProcedureDTO.class);
    }

    /**
     * Obtiene todos los procedimientos.
     * @return Una lista de procedimientos.
     */
    public List<ProcedureDTO> getAllProcedures() {
        List<Procedure> procedures = procedureRepository.findAll();
        return procedures.stream()
                .map(procedure -> modelMapper.map(procedure, ProcedureDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un procedimiento por su ID.
     * @param id ID del procedimiento.
     * @return El procedimiento encontrado.
     * @throws ResponseStatusException si el procedimiento no se encuentra.
     */
    public ProcedureDTO getProcedureById(Long id) {
        Procedure procedure = procedureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Procedure not found"));
        return modelMapper.map(procedure, ProcedureDTO.class);
    }


    /**
     * Obtiene todos los procedimientos asociados a un área por su ID.
     * @param id ID del área.
     * @return Una lista de procedimientos asociados al área.
     */
    public List<ProcedureDTO> getProcedureByAreaId(Long id) {
        List<Procedure> procedures = procedureRepository.findByAreaId(id);
        return procedures.stream()
        .map(procedure -> modelMapper.map(procedure, ProcedureDTO.class))
        .collect(Collectors.toList());
    }

    /**
     * Actualiza un procedimiento por su ID.
     * @param id ID del procedimiento a actualizar.
     * @param createProcedureDTO DTO con los nuevos datos del procedimiento.
     * @return El procedimiento actualizado.
     * @throws ResponseStatusException si el procedimiento no se encuentra o si el área no es válida.
     */
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

    /**
     * Elimina un procedimiento por su ID.
     * @param id ID del procedimiento a eliminar.
     * @throws ResponseStatusException si el procedimiento no se encuentra.
     */
    public void deleteProcedure(Long id) {
        if (!procedureRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Procedure not found");
        }
        procedureRepository.deleteById(id);
    }
}