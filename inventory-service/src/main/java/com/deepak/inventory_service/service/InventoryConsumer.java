package com.deepak.inventory_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.deepak.inventory_service.dto.OrderCreatedEvent;

@Service
public class InventoryConsumer {
    @KafkaListener(topics = "order-created", groupId = "inventory-group")
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("=================================");
        System.out.println("Received Order Event");
        System.out.println(event);
        System.out.println("Reducing stock...");
        System.out.println("=================================");
    }
}
