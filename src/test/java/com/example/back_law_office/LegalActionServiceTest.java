package com.example.back_law_office;

import com.example.back_law_office.dtos.CreateLegalActionDTO;
import com.example.back_law_office.models.ApprovalCode;
import com.example.back_law_office.models.Case;
import com.example.back_law_office.models.LegalAction;
import com.example.back_law_office.models.Procedure;
import com.example.back_law_office.repositories.LegalActionRepository;
import com.example.back_law_office.repositories.ProcedureRepository;
import com.example.back_law_office.services.ApprovalCodeService;
import com.example.back_law_office.services.LegalActionService;
import com.example.back_law_office.services.NotificationProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LegalActionServiceTest {

    @Mock
    private LegalActionRepository legalActionRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ApprovalCodeService approvalCodeService;
    @Mock
    private ProcedureRepository procedureRepository;
    @Mock
    private NotificationProducer notificationProducer;

    @InjectMocks
    private LegalActionService legalActionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba el flujo exitoso de creación de una acción legal cuando el código de aprobación es válido.
     */
    @Test
    void createLegalAction_ShouldSucceed_WhenApprovalCodeIsValid() throws com.fasterxml.jackson.core.JsonProcessingException {
        // Arrange
        CreateLegalActionDTO dto = new CreateLegalActionDTO();
        dto.setApprovalCode("123456");
        dto.setProcedure(1L);

        Case caseEntity = new Case();
        caseEntity.setId(10L);

        ApprovalCode validCode = new ApprovalCode();
        validCode.setId(5L);
        validCode.setCode("123456");
        validCode.setUsed(false);

        Procedure procedure = new Procedure();
        procedure.setId(1L);

        LegalAction mappedLegalAction = new LegalAction();
        LegalAction savedLegalAction = new LegalAction();
        savedLegalAction.setId(100L);

        // Simula el comportamiento de los mocks
        when(approvalCodeService.validateApprovalCode("123456")).thenReturn(Optional.of(validCode));
        when(procedureRepository.findById(1L)).thenReturn(Optional.of(procedure));
        when(modelMapper.map(dto, LegalAction.class)).thenReturn(mappedLegalAction);
        when(legalActionRepository.save(any(LegalAction.class))).thenReturn(savedLegalAction);

        // Act
        LegalAction result = legalActionService.createLegalAction(dto, caseEntity);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getId());

        // Verifica que los métodos clave fueron llamados
        verify(approvalCodeService, times(1)).updateApprovalCode(eq(5L), any(ApprovalCode.class));
        verify(notificationProducer, times(1)).sendNotification(any());
        verify(legalActionRepository, times(1)).save(any(LegalAction.class));
    }

    /**
     * Prueba que se lanza una excepción cuando el código de aprobación es inválido.
     */
    @Test
    void createLegalAction_ShouldThrowException_WhenApprovalCodeIsInvalid() {
        // Arrange
        CreateLegalActionDTO dto = new CreateLegalActionDTO();
        dto.setApprovalCode("invalidCode");
        Case caseEntity = new Case();

        // Simula que el código no es válido
        when(approvalCodeService.validateApprovalCode("invalidCode")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            legalActionService.createLegalAction(dto, caseEntity);
        });

        // Verifica que nunca se intentó guardar nada en el repositorio
        verify(legalActionRepository, never()).save(any());
    }
}
