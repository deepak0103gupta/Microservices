package com.deepak.order_service.service;

import com.deepak.order_service.dto.OrderRequestDto;
import com.deepak.order_service.dto.OrderResponseDto;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto request);
}
