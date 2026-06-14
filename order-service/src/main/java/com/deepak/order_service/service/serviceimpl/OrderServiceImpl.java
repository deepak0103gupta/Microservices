package com.deepak.order_service.service.serviceimpl;

import org.springframework.stereotype.Service;

import com.deepak.order_service.client.ProductClient;
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

    public OrderServiceImpl(OrderRepository orderRepository, ProductClient productServiceClient, ProductLookupService productLookupService) {
        this.orderRepository = orderRepository;
        this.productServiceClient = productServiceClient;
        this.productLookupService = productLookupService;
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
        return new OrderResponseDto(savedOrder.getId(), savedOrder.getProductId(), savedOrder.getQuantity(),
                savedOrder.getStatus());

    }

    @CircuitBreaker(name = "productService", fallbackMethod = "productFallback")
    public ProductResponseDto getProductById(Long productId) {

        return productServiceClient.getProductById(productId);
    }

    public OrderResponseDto createOrderFallback(OrderRequestDto request, Exception ex) {

        return new OrderResponseDto(
                null,
                request.getProductId(),
                request.getQuantity(),
                "PRODUCT_SERVICE_DOWN");
    }
}
