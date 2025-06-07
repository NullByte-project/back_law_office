package com.example.back_law_office;

import com.example.back_law_office.dtos.CreateInterviewDTO;
import com.example.back_law_office.dtos.InterviewDTO;
import com.example.back_law_office.dtos.ClientDTO;
import com.example.back_law_office.dtos.CreateClientDTO;
import com.example.back_law_office.models.Case;
import com.example.back_law_office.models.Client;
import com.example.back_law_office.models.Interview;
import com.example.back_law_office.models.User;
import com.example.back_law_office.repositories.InterviewRepository;
import com.example.back_law_office.repositories.UserRepository;
import com.example.back_law_office.services.ClientService;
import com.example.back_law_office.services.CaseService;
import com.example.back_law_office.services.InterviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest
class BackLawOfficeApplicationTests {

    @Mock
    private InterviewRepository interviewRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClientService clientService;
    @Mock
    private CaseService caseService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private InterviewService interviewService;

    private User user;
    private Client client;
    private Interview savedInterview;
    private InterviewDTO interviewDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuración común para reutilizar en las pruebas
        user = new User();
        user.setId(1L);

        client = new Client();
        client.setId(1L);

        savedInterview = new Interview();
        savedInterview.setAction("placeholder"); // Se puede sobreescribir en cada test

        interviewDTO = new InterviewDTO();
    }

    @Test
    void createInterview_ReturnsInterviewDTO_WhenDataIsValid() {
        CreateInterviewDTO dto = new CreateInterviewDTO();
        dto.setFactualDescription("desc");
        dto.setLegalConcept("concept");
        dto.setAction("other");
        dto.setResponsibleId(1L);
        dto.setClient(new CreateClientDTO());

        client = new Client();
        savedInterview = new Interview();
        interviewDTO = new InterviewDTO();
        savedInterview.setAction("asesoria");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(clientService.createOrUpdateClient(any())).thenReturn(client);
        when(interviewRepository.save(any(Interview.class))).thenReturn(savedInterview);
        when(modelMapper.map(savedInterview, InterviewDTO.class)).thenReturn(interviewDTO);

        InterviewDTO result = interviewService.createInterview(dto);

        assertNotNull(result);
        verify(interviewRepository).save(any(Interview.class));
    }

    @Test
    void createInterview_ThrowsBadRequest_WhenResponsibleNotFound() {
        CreateInterviewDTO dto = new CreateInterviewDTO();
        dto.setResponsibleId(99L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> interviewService.createInterview(dto));
    }

    // =================================================================
    // =================== NUEVAS PRUEBAS AÑADIDAS =====================
    // =================================================================

    /**
     * EVIDENCIA Y REPRODUCCIÓN DEL DEFECTO #6: Lógica de Negocio Basada en "Magic Strings"
     * Esta prueba verifica que cuando la acción es "recepcion", se intenta crear un caso.
     * Demuestra la existencia del flujo condicional que queremos refactorizar.
     */
    @Test
    void createInterview_CallsCaseService_WhenActionIsRecepcion() {
        // Arrange
        CreateInterviewDTO dto = new CreateInterviewDTO();
        dto.setAction("recepcion"); // La "magic string" que dispara la creación del caso
        dto.setResponsibleId(1L);
        dto.setClient(new CreateClientDTO());

        savedInterview.setAction("recepcion");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(clientService.createOrUpdateClient(any(CreateClientDTO.class))).thenReturn(client);
        when(interviewRepository.save(any(Interview.class))).thenReturn(savedInterview);
        when(caseService.createCase(any(), any())).thenReturn(new Case()); // Simula una creación de caso exitosa
        when(modelMapper.map(savedInterview, InterviewDTO.class)).thenReturn(interviewDTO);

        // Act
        interviewService.createInterview(dto);

        // Assert
        // La aserción más importante aquí es verificar que el método createCase fue llamado
        verify(caseService, times(1)).createCase(any(), any());
    }

    /**
     * PRUEBA DE CASO DE ERROR NO CONTEMPLADO: Falla en la creación del cliente.
     * Esta prueba simula un escenario donde el servicio de cliente devuelve null.
     * El sistema debe lanzar una excepción y no continuar.
     */
    @Test
    void createInterview_ThrowsBadRequest_WhenClientCreationFails() {
        // Arrange
        CreateInterviewDTO dto = new CreateInterviewDTO();
        dto.setResponsibleId(1L);
        dto.setClient(new CreateClientDTO());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Simulamos que el servicio de cliente falla y devuelve null
        when(clientService.createOrUpdateClient(any(CreateClientDTO.class))).thenReturn(null);

        // Act & Assert
        // Verificamos que se lanza la excepción correcta
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            interviewService.createInterview(dto);
        });

        // Verificamos que el mensaje de error es el esperado
        assertNotNull(exception.getReason());
        assertTrue(exception.getReason().contains("Invalid client data"));
        // Nos aseguramos de que no se intentó guardar la entrevista
        verify(interviewRepository, never()).save(any());
    }

    /**
     * PRUEBA DE CASO DE ERROR NO CONTEMPLADO: Falla en la creación del caso.
     * Esta prueba simula el flujo donde la acción es "recepcion", pero la creación del caso falla.
     * El sistema debe lanzar una excepción.
     */
    @Test
    void createInterview_ThrowsBadRequest_WhenCaseCreationFailsAndActionIsRecepcion() {
        // Arrange
        CreateInterviewDTO dto = new CreateInterviewDTO();
        dto.setAction("recepcion");
        dto.setResponsibleId(1L);
        dto.setClient(new CreateClientDTO());

        savedInterview.setAction("recepcion");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(clientService.createOrUpdateClient(any(CreateClientDTO.class))).thenReturn(client);
        when(interviewRepository.save(any(Interview.class))).thenReturn(savedInterview);
        // Simulamos que el servicio de caso falla y devuelve null
        when(caseService.createCase(any(), any())).thenReturn(null);

        // Act & Assert
        // Verificamos que se lanza la excepción esperada
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            interviewService.createInterview(dto);
        });

        assertTrue(exception.getReason().contains("Invalid client data"));
    }
}
