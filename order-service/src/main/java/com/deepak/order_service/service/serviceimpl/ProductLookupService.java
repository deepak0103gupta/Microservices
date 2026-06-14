package com.deepak.order_service.service.serviceimpl;

import org.springframework.stereotype.Service;

import com.deepak.order_service.client.ProductClient;
import com.deepak.order_service.dto.ProductResponseDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ProductLookupService {
    private final ProductClient productClient;

    public ProductLookupService(ProductClient productClient) {
        this.productClient = productClient;
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "productFallback")
    public ProductResponseDto getProductById(Long productId) {

        return productClient.getProductById(productId);
    }

    public ProductResponseDto productFallback(
            Long productId,
            Exception ex) {

        throw new RuntimeException(
                "Product Service is unavailable");
    }
}
