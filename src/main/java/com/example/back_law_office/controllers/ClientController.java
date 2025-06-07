package com.example.back_law_office.controllers;

import com.example.back_law_office.dtos.CreateClientDTO;
import com.example.back_law_office.dtos.CreateSocioeconomicStudyDTO;
import com.example.back_law_office.dtos.ClientDTO;
import com.example.back_law_office.services.ClientService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /*
        * Método anterios con validación dentro del controlador.
     * @PostMapping("/create")
     * public ResponseEntity<?> createClient(@Valid @RequestBody CreateClientDTO
     * createClientDTO,
     * BindingResult bindingResult) {
     * if (bindingResult.hasErrors()) {
     * List<String> errors = bindingResult.getFieldErrors().stream()
     * .map(fieldError -> fieldError.getDefaultMessage())
     * .collect(Collectors.toList());
     * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
     * }
     * 
     * ClientDTO clientDTO = clientService.createClient(createClientDTO);
     * return ResponseEntity.status(HttpStatus.CREATED).body(clientDTO);
     * }
     */

    // Archivo: ClientController.java (DESPUÉS)
    @PostMapping("/create")
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody CreateClientDTO createClientDTO) {
        // La validación ahora es manejada por el GlobalExceptionHandler.
        // El método es más limpio y se enfoca en su responsabilidad principal.
        ClientDTO clientDTO = clientService.createClient(createClientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDTO);
    }

    // NUEVO ARCHIVO: GlobalExceptionHandler.java (en un paquete de 'exceptions' o
    // similar)
    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
            List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
    }

    @PutMapping("/socioeconomicStudy/{clientId}")
    public ResponseEntity<?> socioEconomicStudy(@Valid @RequestBody CreateSocioeconomicStudyDTO entity,
            @PathVariable Long clientId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientService.setSocioeconomicStudy(clientId, entity));
    }

    // Obtener todos los clientes
    @GetMapping("all")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        ClientDTO clientDTO = clientService.getClientById(id);
        return ResponseEntity.ok(clientDTO);
    }

    // Actualizar un cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody CreateClientDTO createClientDTO) {
        ClientDTO updatedClient = clientService.updateClient(id, createClientDTO);
        return ResponseEntity.ok(updatedClient);
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}