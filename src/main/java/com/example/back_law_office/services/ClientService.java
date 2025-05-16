package com.example.back_law_office.services;

import com.example.back_law_office.dtos.CreateClientDTO;
import com.example.back_law_office.dtos.CreateSocioeconomicStudyDTO;
import com.example.back_law_office.dtos.ClientDTO;
import com.example.back_law_office.models.Client;
import com.example.back_law_office.models.SocioEconomicStudy;
import com.example.back_law_office.repositories.ClientRepository;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Crea un nuevo cliente.
     * @param createClientDTO
     * @return El cliente creado.
     */
    public ClientDTO createClient(CreateClientDTO createClientDTO){
        try{
            
            Client client = modelMapper.map(createClientDTO, Client.class);
            if (client.getDiferentialApproaches() != null) {
                client.getDiferentialApproaches().setId(null);
            }
            Client savedClient = clientRepository.save(client);
            return modelMapper.map(savedClient, ClientDTO.class);
        }catch(Exception e){
            this.handleException(e);
            return null;
        }
    }

    /**
     * Asocia un estudio socioeconómico a un cliente existente.
     * @param clientId id del cliente
     * @param socioeconomicStudyDTO DTO del estudio socioeconómico
     * @return El cliente actualizado con el estudio socioeconómico asociado.
     * @throws ResponseStatusException si el cliente no se encuentra.
     */
    public ClientDTO setSocioeconomicStudy(Long clientId, CreateSocioeconomicStudyDTO socioeconomicStudyDTO) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con ID: " + clientId));
        
        client.setSocioeconomicStudy(modelMapper.map(socioeconomicStudyDTO, SocioEconomicStudy.class));

        client.getSocioeconomicStudy().setId(null); 
        Client updatedClient = clientRepository.save(client);
        return modelMapper.map(updatedClient, ClientDTO.class);
    }

    /**
     * Obtiene todos los clientes.
     * @return Una lista de clientes.
     */
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un cliente por su ID.
     * @param id El ID del cliente.
     * @return El cliente encontrado.
     * @throws ResponseStatusException si el cliente no se encuentra.
     */
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        return modelMapper.map(client, ClientDTO.class);
    }

    /**
     * Actualiza un cliente existente.
     * @param id El ID del cliente a actualizar.
     * @param createClientDTO Los nuevos datos del cliente.
     * @return El cliente actualizado.
     */
    public ClientDTO updateClient(Long id, CreateClientDTO createClientDTO) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        modelMapper.map(createClientDTO, existingClient); // Actualiza los campos del cliente existente
        Client updatedClient = clientRepository.save(existingClient);
        return modelMapper.map(updatedClient, ClientDTO.class);
    }

    /**
     * Elimina un cliente por su ID.
     * @param id El ID del cliente a eliminar.
     */
    public void deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
    }

    /**
     * Maneja excepciones y lanza una ResponseStatusException con el mensaje adecuado.
     * @param e La excepción a manejar.
     */
    private void handleException(Exception e) {
        if (e instanceof DataIntegrityViolationException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error de integridad de datos: " + e.getMessage(), e);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor: " + e.getMessage(), e);
        }
    }

    /**
     * Crea o actualiza un cliente.
     * @param createClientDTO DTO del cliente a crear o actualizar.
     * @return El cliente creado o actualizado.
     */
    public Client createOrUpdateClient(CreateClientDTO createClientDTO) {
        try {

            Client client = this.clientRepository.findByIdentification(createClientDTO.getIdentification())
                    .orElse(new Client()); 
    
            modelMapper.map(createClientDTO, client);
            return clientRepository.save(client);
        } catch (Exception e) {
            this.handleException(e);
            return null;
        }
    }
}