package com.example.back_law_office;

import com.example.back_law_office.dtos.CreateUserDTO;
import com.example.back_law_office.dtos.UserDTO;
import com.example.back_law_office.models.User;
import com.example.back_law_office.repositories.UserRepository;
import com.example.back_law_office.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks para cada prueba
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que el método createUser funciona correctamente.
     * Verifica que la contraseña se codifica y que el usuario se guarda.
     */
    @Test
    void createUser_ShouldEncodePasswordAndSaveUser() {
        // Arrange (Preparar)
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername("testuser");
        createUserDTO.setPassword("plainPassword123");
        createUserDTO.setEmail("test@example.com");

        User userToSave = new User();
        userToSave.setUsername("testuser");
        userToSave.setEmail("test@example.com");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("encodedPassword");

        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setId(1L);
        expectedUserDTO.setUsername("testuser");

        // Simula el comportamiento de las dependencias (mocks)
        when(passwordEncoder.encode("plainPassword123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(modelMapper.map(savedUser, UserDTO.class)).thenReturn(expectedUserDTO);

        // Act (Actuar)
        UserDTO result = userService.createUser(createUserDTO);

        // Assert (Afirmar)
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());

        // Verifica que los métodos de los mocks fueron llamados como se esperaba
        verify(passwordEncoder, times(1)).encode("plainPassword123");
        verify(userRepository, times(1)).save(any(User.class));
    }
}
