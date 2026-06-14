package com.deepak.product_service.service;

import java.util.List;

import com.deepak.product_service.dto.ProductRequestDto;
import com.deepak.product_service.dto.ProductResponseDto;


public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);
    ProductResponseDto getProductById(Long id);
    List<ProductResponseDto> getAllProducts();
}
