package com.example.back_law_office.services;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back_law_office.dtos.CreateInterviewDTO;
import com.example.back_law_office.dtos.InterviewDTO;
import com.example.back_law_office.models.Interview;
import com.example.back_law_office.models.Client;
import com.example.back_law_office.models.User;
import com.example.back_law_office.repositories.InterviewRepository;
import com.example.back_law_office.repositories.ClientRepository;
import com.example.back_law_office.repositories.UserRepository;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    public InterviewDTO createInterview(CreateInterviewDTO interviewDTO) {
        Interview interview = new Interview();
        interview.setFactualDescription(interviewDTO.getFactualDescription());
        interview.setLegalConcept(interviewDTO.getLegalConcept());
        interview.setAction(interviewDTO.getAction());

        User responsible = userRepository.findById(interviewDTO.getResponsibleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid responsible id"));
        Client client = clientRepository.findById(interviewDTO.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid client id"));

        interview.setResponsible(responsible);
        interview.setClient(client);

        LocalDateTime creationDate = LocalDateTime.now(ZoneId.of("America/Bogota"));
        interview.setCreationDate(creationDate);

        Interview savedInterview = interviewRepository.save(interview);
        return modelMapper.map(savedInterview, InterviewDTO.class);
    }
}