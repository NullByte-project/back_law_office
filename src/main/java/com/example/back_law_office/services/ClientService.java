package com.example.back_law_office.services;

import com.example.back_law_office.dtos.CreateClientDTO;
import com.example.back_law_office.dtos.ClientDTO;
import com.example.back_law_office.models.Client;
import com.example.back_law_office.repositories.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Crear un cliente
    public ClientDTO createClient(CreateClientDTO createClientDTO) {
        Client client = modelMapper.map(createClientDTO, Client.class);
    
        if (client.getSocioeconomicStudy() != null) {
            client.getSocioeconomicStudy().setId(null);
        }
        Client savedClient = clientRepository.save(client);
        return modelMapper.map(savedClient, ClientDTO.class);
    }

    // Obtener todos los clientes
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

    // Obtener un cliente por ID
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        return modelMapper.map(client, ClientDTO.class);
    }

    // Actualizar un cliente
    public ClientDTO updateClient(Long id, CreateClientDTO createClientDTO) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        modelMapper.map(createClientDTO, existingClient); // Actualiza los campos del cliente existente
        Client updatedClient = clientRepository.save(existingClient);
        return modelMapper.map(updatedClient, ClientDTO.class);
    }

    // Eliminar un cliente
    public void deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
    }
}