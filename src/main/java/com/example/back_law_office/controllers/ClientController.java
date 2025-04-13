package com.example.back_law_office.controllers;

import com.example.back_law_office.dtos.CreateClientDTO;
import com.example.back_law_office.dtos.ClientDTO;
import com.example.back_law_office.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Crear un cliente
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody CreateClientDTO createClientDTO) {
        ClientDTO clientDTO = clientService.createClient(createClientDTO);
        return ResponseEntity.ok(clientDTO);
    }

    // Obtener todos los clientes
    @GetMapping
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