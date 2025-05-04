package com.example.back_law_office.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.back_law_office.dtos.CreateCaseDTO;
import com.example.back_law_office.dtos.CreateLegalActionDTO;
import com.example.back_law_office.dtos.LegalActionDTO;
import com.example.back_law_office.helpers.enums.State;
import com.example.back_law_office.models.Case;
import com.example.back_law_office.models.Interview;
import com.example.back_law_office.models.LegalAction;
import com.example.back_law_office.repositories.CaseRepository;


import java.time.LocalDateTime;


@Service
public class CaseService {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private LegalActionService legalActionService;

    @Autowired
    private ModelMapper modelMapper;


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

    public LegalActionDTO addLegalActionToCase(Long caseId, CreateLegalActionDTO legalActionDTO) {
        Case existingCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));
        LegalAction legalAction = legalActionService.createLegalAction(legalActionDTO, existingCase);
        return modelMapper.map(legalAction, LegalActionDTO.class);
    }

}