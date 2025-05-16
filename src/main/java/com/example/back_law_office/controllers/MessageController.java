package com.example.back_law_office.controllers;

import com.example.back_law_office.dtos.MessageDTO;
import com.example.back_law_office.services.NotificationProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



//Controlador de prueba para enviar mensajes usando RabbitMQ
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    
    private NotificationProducer notificationProducer;

    public MessageController(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    @RequestMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO message) {
        try {
            notificationProducer.sendNotification(message);
            return ResponseEntity.ok("Message sent to RabbitMQ: ");
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).body("Error sending message: " + e.getMessage());
        }
    }
}
