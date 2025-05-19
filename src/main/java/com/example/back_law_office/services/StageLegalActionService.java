package com.example.back_law_office.services;

import com.example.back_law_office.dtos.CreateStageLegalActionDTO;
import com.example.back_law_office.dtos.StageLegalActionDTO;
import com.example.back_law_office.models.LegalAction;
import com.example.back_law_office.models.Stage;
import com.example.back_law_office.models.StageLegalAction;
import com.example.back_law_office.repositories.LegalActionRepository;
import com.example.back_law_office.repositories.StageLegalActionRepository;
import com.example.back_law_office.repositories.StageRepository;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class StageLegalActionService {

    @Autowired
    private StageLegalActionRepository stageLegalActionRepository;

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private LegalActionRepository legalActionRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Crea una nueva relación entre Stage y LegalAction.
     * @param stageLegalAction DTO con los datos de la relación a crear.
     * @return La relación creada.
     * @throws ResponseStatusException si el ID de la etapa o la acción legal no son válidos.
     */
    public StageLegalActionDTO createStageLegalAction(CreateStageLegalActionDTO stageLegalAction) {
        StageLegalAction newStageLegalAction = modelMapper.map(stageLegalAction, StageLegalAction.class);
        Stage stage = stageRepository.findById(stageLegalAction.getStageId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid stage id")); 
        
        newStageLegalAction.setStage(stage);
        LegalAction legalAction = legalActionRepository.findById(stageLegalAction.getLegalActionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid legal action id"));
        
        newStageLegalAction.setLegalAction(legalAction);
        LocalDateTime now = LocalDateTime.now(java.time.ZoneId.of("America/Bogota"));
        newStageLegalAction.setStartDate(now);

        newStageLegalAction.setId(null); // Asegúrate de que el ID sea nulo antes de guardar
        StageLegalAction savedStageLegalAction = stageLegalActionRepository.save(newStageLegalAction);


        return modelMapper.map(savedStageLegalAction, StageLegalActionDTO.class);
    }

    /**
     * Obtiene todas las relaciones entre Stage y LegalAction.
     * @return Una lista de relaciones.
     */
    public List<StageLegalAction> getAllStageLegalActions() {
        return stageLegalActionRepository.findAll();
    }

    /**
     * Obtiene una relación entre Stage y LegalAction por su ID.
     * @param id ID de la relación.
     * @return La relación encontrada.
     * @throws ResponseStatusException si la relación no se encuentra.
     */
    public StageLegalAction getStageLegalActionById(Long id) {
        return stageLegalActionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "StageLegalAction not found"));
    }

    /**
     * Actualiza una relación entre Stage y LegalAction por su ID.
     * @param id ID de la relación a actualizar.
     * @param updatedStageLegalAction DTO con los nuevos datos de la relación.
     * @return La relación actualizada.
     * @throws ResponseStatusException si la relación no se encuentra.
     */
    public StageLegalAction updateStageLegalAction(Long id, StageLegalAction updatedStageLegalAction) {
        return stageLegalActionRepository.findById(id).map(existingStageLegalAction -> {
            existingStageLegalAction.setComments(updatedStageLegalAction.getComments());
            existingStageLegalAction.setInternalDeadline(updatedStageLegalAction.getInternalDeadline());
            existingStageLegalAction.setLegalAction(updatedStageLegalAction.getLegalAction());
            existingStageLegalAction.setStage(updatedStageLegalAction.getStage());
            existingStageLegalAction.setStartDate(updatedStageLegalAction.getStartDate());
            return stageLegalActionRepository.save(existingStageLegalAction);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "StageLegalAction not found"));
    }

    /**
     * Elimina una relación entre Stage y LegalAction por su ID.
     * @param id ID de la relación a eliminar.
     * @throws ResponseStatusException si la relación no se encuentra.
     */
    public void deleteStageLegalAction(Long id) {
        if (!stageLegalActionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "StageLegalAction not found");
        }
        stageLegalActionRepository.deleteById(id);
    }
    public List<StageLegalActionDTO> getStagesByLegalActionId(Long legalActionId) {
        // Verifica que la acción legal exista
        LegalAction legalAction = legalActionRepository.findById(legalActionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LegalAction not found"));

        // Obtiene las relaciones StageLegalAction asociadas a la acción legal
        @SuppressWarnings("unchecked")
        List<StageLegalAction> stageLegalActions = (List<StageLegalAction>) stageLegalActionRepository.findByLegalActionId(legalActionId);

        // Mapea la lista de entidades a DTOs
        return stageLegalActions.stream()
                .map(stageLegalAction -> modelMapper.map(stageLegalAction, StageLegalActionDTO.class))
                .toList();
    }
}