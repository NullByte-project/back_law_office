package com.example.back_law_office.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.example.back_law_office.dtos.MessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.back_law_office.config.RabbitMQConfig;

@Service
public class NotificationProducer {
    
    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(MessageDTO message) throws JsonProcessingException {
        String jsonMessage = new ObjectMapper().writeValueAsString(message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, jsonMessage);
    }

    
}
