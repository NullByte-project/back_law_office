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
    
    /**
     * Crea una nueva actuación legal.
     * @param legalAction DTO con los datos de la actuación legal a crear.
     * @param caseEntity Caso asociado a la actuación legal.
     * @throws ResponseStatusException si el código de aprobación no es válido o si la actuación legal no se puede crear.
     * @return
     */
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
            return savedLegalAction;
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid approval code");
        }
    }

    /**
     * Obtiene todas las acciones legales.
     * @return Una lista de acciones legales.
     */
    public List<LegalAction> getAllLegalActions() {
        return legalActionRepository.findAll();
    }

    /**
     * Obtiene una acción legal por su ID.
     * @param id El ID de la acción legal.
     * @return La acción legal encontrada.
     * @throws ResponseStatusException si la acción legal no se encuentra.
     */
    public Optional<LegalAction> getLegalActionById(Long id) {
        return legalActionRepository.findById(id);
    }

    
    /**
     * Actualiza una acción legal existente.
     * @param id El ID de la acción legal a actualizar.
     */
    public void deleteLegalAction(Long id) {
        if (legalActionRepository.existsById(id)) {
            legalActionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Acción legal no encontrada con ID: " + id);
        }
    }
}