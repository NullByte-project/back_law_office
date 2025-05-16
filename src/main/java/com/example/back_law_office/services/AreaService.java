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

    /**
     * Crea una nueva área.
     * @param createAreaDTO
     * @return El área creada.
     * @throws ResponseStatusException si el asistente no es válido.
     */
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

    /**
     * Obtiene todas las áreas.
     * @return Una lista de áreas.
     */
    public List<AreaDTO> getAllAreas() {
        List<Area> areas = areaRepository.findAll();
        return areas.stream()
                .map(area -> modelMapper.map(area, AreaDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un área por su ID.
     * @param id El ID del área.
     * @return El área encontrada.
     * @throws ResponseStatusException si el área no se encuentra.
     */
    public AreaDTO getAreaById(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Area not found"));
        return modelMapper.map(area, AreaDTO.class);
    }

    
    /**
     * Actualiza un área por su ID.
     * @param id El ID del área a actualizar.
     * @param createAreaDTO Los nuevos datos del área.
     * @return El área actualizada.
     * @throws ResponseStatusException si el área no se encuentra o el asistente no es válido.
     */
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

    /**
     * Elimina un área por su ID.
     * @param id El ID del área a eliminar.
     * @throws ResponseStatusException si el área no se encuentra.
     */
    public void deleteArea(Long id) {
        if (!areaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Area not found");
        }
        areaRepository.deleteById(id);
    }
}