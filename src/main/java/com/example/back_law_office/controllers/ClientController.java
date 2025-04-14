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
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@Valid @RequestBody CreateClientDTO createClientDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        ClientDTO clientDTO = clientService.createClient(createClientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDTO);
    }


    @PutMapping("/socioeconomicStudy/{clientId}")
    public ResponseEntity<?> socioEconomicStudy(@Valid @RequestBody CreateSocioeconomicStudyDTO entity, @PathVariable Long clientId, BindingResult bindingResult) {
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