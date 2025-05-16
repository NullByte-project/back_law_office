package com.example.back_law_office.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.back_law_office.dtos.CaseDTO;
import com.example.back_law_office.dtos.CreateCaseDTO;
import com.example.back_law_office.dtos.CreateLegalActionDTO;
import com.example.back_law_office.dtos.LegalActionDTO;
import com.example.back_law_office.dtos.ListCasesDTO;
import com.example.back_law_office.helpers.enums.State;
import com.example.back_law_office.models.Case;
import com.example.back_law_office.models.Interview;
import com.example.back_law_office.models.LegalAction;
import com.example.back_law_office.repositories.CaseRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CaseService {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private LegalActionService legalActionService;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * Crea un nuevo caso.
     *
     * @param createCaseDTO DTO con los datos del caso a crear.
     * @param interview     Entrevista asociada al caso.
     * @return El caso creado.
     */
    public Case createCase(CreateCaseDTO createCaseDTO, Interview interview) {
        Case newCase = new Case();
        LocalDateTime now = LocalDateTime.now(java.time.ZoneId.of("America/Bogota"));
        newCase.setCreationDate(now);
        newCase.setState(State.ACTIVE);
        newCase.setFolder(createCaseDTO.getFolder());
        newCase.setInterview(interview);
        Case savedCase = caseRepository.save(newCase);
        LegalAction legalAction = legalActionService.createLegalAction(createCaseDTO.getLegalAction(), newCase);
        if (legalAction == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating legal action");
        }
        return savedCase;
    }


    /**
     * Obtiene un caso por su ID.
     * @param id El ID del caso.
     * @return El caso encontrado.
     * @throws ResponseStatusException si el caso no se encuentra.
     */
    public CaseDTO getCaseById(Long id) {
        Case existingCase = caseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));
        return modelMapper.map(existingCase, CaseDTO.class);
    }


    /**
     * Obtiene todos los casos.
     * @return Una lista de casos.
     */
    public List<ListCasesDTO> getAllCases() {
        return caseRepository.findAll().stream()
                .map(caseEntity -> modelMapper.map(caseEntity, ListCasesDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los casos con acción legal en un área específica.
     * @param areaId El ID del área.
     * @return Una lista de casos con acción legal en el área especificada.
     * @throws ResponseStatusException si el área no se encuentra.
     */
    public List<ListCasesDTO> getCasesByArea(Long areaId) {
        return caseRepository.findCasesWithLegalActionInArea(areaId).stream()
                .map(caseEntity -> modelMapper.map(caseEntity, ListCasesDTO.class))
                .collect(Collectors.toList());
    }


    /**
     * Agrega una acción legal a un caso existente.
     * @param caseId El ID del caso al que se le agregará la acción legal.
     * @param legalActionDTO DTO con los datos de la acción legal a agregar.
     * @return El DTO de la acción legal agregada.
     * @throws ResponseStatusException si el caso no se encuentra.
     */
    public LegalActionDTO addLegalActionToCase(Long caseId, CreateLegalActionDTO legalActionDTO) {
        Case existingCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));
        LegalAction legalAction = legalActionService.createLegalAction(legalActionDTO, existingCase);
        return modelMapper.map(legalAction, LegalActionDTO.class);
    }

}