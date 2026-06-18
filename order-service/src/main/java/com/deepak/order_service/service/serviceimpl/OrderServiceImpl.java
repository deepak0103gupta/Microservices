package com.deepak.order_service.service.serviceimpl;

import org.springframework.stereotype.Service;

import com.deepak.order_service.client.ProductClient;
import com.deepak.order_service.dto.OrderCreatedEvent;
import com.deepak.order_service.dto.OrderRequestDto;
import com.deepak.order_service.dto.OrderResponseDto;
import com.deepak.order_service.dto.ProductResponseDto;
import com.deepak.order_service.entity.Order;
import com.deepak.order_service.exception.QunatityUnavailable;
import com.deepak.order_service.repository.OrderRepository;
import com.deepak.order_service.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productServiceClient;
    private final ProductLookupService productLookupService;
    private final OrderProducer orderProducer;
    public OrderServiceImpl(OrderRepository orderRepository, ProductClient productServiceClient, ProductLookupService productLookupService,OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.productServiceClient = productServiceClient;
        this.productLookupService = productLookupService;
        this.orderProducer = orderProducer;
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto request) {
        ProductResponseDto product = productLookupService.getProductById(request.getProductId());
        if (product.getStock() < request.getQuantity()) {
            throw new QunatityUnavailable("Insufficient stock for product ID: " + request.getProductId());
        }
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setStatus("CREATED");
        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent event =
        new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getProductId(),
                savedOrder.getQuantity()
        );
        orderProducer.publishOrderCreatedEvent(event);
        return new OrderResponseDto(savedOrder.getId(), savedOrder.getProductId(), savedOrder.getQuantity(),
                savedOrder.getStatus());

    }


}
