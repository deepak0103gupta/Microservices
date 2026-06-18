package com.deepak.order_service.service.serviceimpl;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.deepak.order_service.dto.OrderCreatedEvent;

@Service
public class OrderProducer {
    
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send("order-created", event);
        System.out.println("Published OrderCreatedEvent : " + event);
    }
}
