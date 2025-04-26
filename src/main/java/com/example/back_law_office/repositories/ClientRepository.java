package com.example.back_law_office.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back_law_office.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findById(long id);
    Optional<Client> findByIdentification(String cc);
    
} 
