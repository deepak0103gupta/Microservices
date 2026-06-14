package com.deepak.order_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.order_service.dto.OrderRequestDto;
import com.deepak.order_service.dto.OrderResponseDto;

import com.deepak.order_service.service.OrderService;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto request) {
 
            OrderResponseDto response = orderService.createOrder(request);
            return ResponseEntity.ok(response);

    }
}
