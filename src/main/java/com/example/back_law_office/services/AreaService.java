package com.example.back_law_office.services;

import com.example.back_law_office.dtos.CreateAreaDTO;
import com.example.back_law_office.dtos.AreaDTO;
import com.example.back_law_office.models.Area;
import com.example.back_law_office.models.User;
import com.example.back_law_office.repositories.AreaRepository;
import com.example.back_law_office.repositories.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Crear un área
    public AreaDTO createArea(CreateAreaDTO createAreaDTO) {
        User assistant = null;
        if (createAreaDTO.getAssistantId() != null) {
            assistant = userRepository.findById(createAreaDTO.getAssistantId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid assistant ID"));
        }

        Area area = modelMapper.map(createAreaDTO, Area.class);
        area.setAssistant(assistant);

        Area savedArea = areaRepository.save(area);
        return modelMapper.map(savedArea, AreaDTO.class);
    }

    // Obtener todas las áreas
    public List<AreaDTO> getAllAreas() {
        List<Area> areas = areaRepository.findAll();
        return areas.stream()
                .map(area -> modelMapper.map(area, AreaDTO.class))
                .collect(Collectors.toList());
    }

    // Obtener un área por ID
    public AreaDTO getAreaById(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Area not found"));
        return modelMapper.map(area, AreaDTO.class);
    }

    // Actualizar un área existente
    public AreaDTO updateArea(Long id, CreateAreaDTO createAreaDTO) {
        Area existingArea = areaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Area not found"));

        User assistant = null;
        if (createAreaDTO.getAssistantId() != null) {
            assistant = userRepository.findById(createAreaDTO.getAssistantId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid assistant ID"));
        }

        modelMapper.map(createAreaDTO, existingArea);
        existingArea.setAssistant(assistant);

        Area updatedArea = areaRepository.save(existingArea);
        return modelMapper.map(updatedArea, AreaDTO.class);
    }

    // Eliminar un área por ID
    public void deleteArea(Long id) {
        if (!areaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Area not found");
        }
        areaRepository.deleteById(id);
    }
}