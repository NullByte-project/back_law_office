package com.example.back_law_office.services;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.back_law_office.dtos.CreateInterviewDTO;
import com.example.back_law_office.dtos.InterviewDTO;
import com.example.back_law_office.models.Interview;
import com.example.back_law_office.models.Case;
import com.example.back_law_office.models.Client;
import com.example.back_law_office.models.User;
import com.example.back_law_office.repositories.InterviewRepository;
import com.example.back_law_office.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CaseService caseService;


    /**
     * Crea una nueva entrevista.
     * @param interviewDTO DTO con los datos de la entrevista a crear.
     * @return La entrevista creada.
     * @throws ResponseStatusException si el responsable o el cliente no son válidos.
     */
    @Transactional(rollbackOn = Exception.class)
    public InterviewDTO createInterview(CreateInterviewDTO interviewDTO) {
        Interview interview = new Interview();
        interview.setFactualDescription(interviewDTO.getFactualDescription());
        interview.setLegalConcept(interviewDTO.getLegalConcept());
        interview.setAction(interviewDTO.getAction());

        User responsible = userRepository.findById(interviewDTO.getResponsibleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid responsible id"));
        Client client = clientService.createOrUpdateClient(interviewDTO.getClient());
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid client data");
        }

        interview.setResponsible(responsible);
        interview.setClient(client);
        LocalDateTime creationDate = LocalDateTime.now(ZoneId.of("America/Bogota"));
        interview.setCreationDate(creationDate);
        Interview savedInterview = interviewRepository.save(interview);
        
        if(savedInterview.getAction().equals("recepcion")){
            Case newCase = caseService.createCase(interviewDTO.getLegalCase(), savedInterview);
            if (newCase == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid client data");
            }
        }

        return modelMapper.map(savedInterview, InterviewDTO.class);
    }


}