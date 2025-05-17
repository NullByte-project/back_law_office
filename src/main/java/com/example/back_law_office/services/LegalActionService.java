package com.example.back_law_office.services;

import com.example.back_law_office.dtos.CreateLegalActionDTO;
import com.example.back_law_office.models.ApprovalCode;
import com.example.back_law_office.models.Case;
import com.example.back_law_office.models.LegalAction;
import com.example.back_law_office.models.Procedure;
import com.example.back_law_office.repositories.LegalActionRepository;
import com.example.back_law_office.repositories.ProcedureRepository;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LegalActionService {

    @Autowired
    private LegalActionRepository legalActionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApprovalCodeService approvalCodeService;

    @Autowired 
    private ProcedureRepository procedureRepository;

    @Autowired
    private NotificationProducer notificationProducer;
    // Crear una nueva acción legal

    public LegalAction createLegalAction(CreateLegalActionDTO legalAction, Case caseEntity) {
        Optional<ApprovalCode> approvalCode = approvalCodeService.validateApprovalCode(legalAction.getApprovalCode());
        if (approvalCode.isPresent()) {
            LegalAction newLegalAction = modelMapper.map(legalAction, LegalAction.class);
            newLegalAction.setLCase(caseEntity);
            Procedure procedure = procedureRepository.findById(legalAction.getProcedure())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid procedure id"));
            newLegalAction.setProcedure(procedure);
            LegalAction savedLegalAction = legalActionRepository.save(newLegalAction);
            ApprovalCode newApprovalCode = approvalCode.get();
            newApprovalCode.setUsed(true);
            newApprovalCode.setLegalAction(savedLegalAction);
            approvalCodeService.updateApprovalCode(newApprovalCode.getId(), newApprovalCode);

            // Enviar notificación de creación de acción legal
            //sendNotification(savedLegalAction);

            return savedLegalAction;
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid approval code");
        }
    }

    // Obtener todas las acciones legales
    public List<LegalAction> getAllLegalActions() {
        return legalActionRepository.findAll();
    }

    // Obtener una acción legal por ID
    public Optional<LegalAction> getLegalActionById(Long id) {
        return legalActionRepository.findById(id);
    }

    // Actualizar una acción legal existente
    /* public LegalAction updateLegalAction(Long id, LegalAction updatedLegalAction) {
        return legalActionRepository.findById(id).map(existingLegalAction -> {
            existingLegalAction.setCaseId(updatedLegalAction.getCaseId());
            existingLegalAction.setProcedure(updatedLegalAction.getProcedure());
            existingLegalAction.setInstructios(updatedLegalAction.getInstructios());
            existingLegalAction.setAdditionalInfo(updatedLegalAction.getAdditionalInfo());
            return legalActionRepository.save(existingLegalAction);
        }).orElseThrow(() -> new RuntimeException("Acción legal no encontrada con ID: " + id));
    }
 */
    // Eliminar una acción legal por ID
    public void deleteLegalAction(Long id) {
        if (legalActionRepository.existsById(id)) {
            legalActionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Acción legal no encontrada con ID: " + id);
        }
    }

    //Enviar notificacion de creaccion de accion legal
    public void sendNotification(LegalAction legalAction) {
        try {
            notificationProducer.sendLegalActionNotification(legalAction.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar la notificación: " + e.getMessage());
        }
    }

}