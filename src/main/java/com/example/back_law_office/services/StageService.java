package com.example.back_law_office.services;

import com.example.back_law_office.dtos.CreateStageDTO;
import com.example.back_law_office.dtos.StageDTO;
import com.example.back_law_office.models.Stage;
import com.example.back_law_office.repositories.StageRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StageService {

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Crear una nueva etapa
    public StageDTO createStage(CreateStageDTO createStageDTO) {
        Stage stage = modelMapper.map(createStageDTO, Stage.class);
        Stage savedStage = stageRepository.save(stage);
        return modelMapper.map(savedStage, StageDTO.class);
    }

    // Obtener todas las etapas
    public List<StageDTO> getAllStages() {
        List<Stage> stages = stageRepository.findAll();
        return stages.stream()
                .map(stage -> modelMapper.map(stage, StageDTO.class))
                .collect(Collectors.toList());
    }

    // Obtener una etapa por ID
    public StageDTO getStageById(Long id) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stage not found"));
        return modelMapper.map(stage, StageDTO.class);
    }

    // Actualizar una etapa existente
    public StageDTO updateStage(Long id, CreateStageDTO createStageDTO) {
        Stage existingStage = stageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stage not found"));

        modelMapper.map(createStageDTO, existingStage);
        Stage updatedStage = stageRepository.save(existingStage);
        return modelMapper.map(updatedStage, StageDTO.class);
    }

    // Eliminar una etapa por ID
    public void deleteStage(Long id) {
        if (!stageRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stage not found");
        }
        stageRepository.deleteById(id);
    }
}