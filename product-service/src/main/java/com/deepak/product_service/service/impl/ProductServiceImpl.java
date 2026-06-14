package com.deepak.product_service.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepak.product_service.dto.ProductRequestDto;
import com.deepak.product_service.dto.ProductResponseDto;
import com.deepak.product_service.entity.Product;
import com.deepak.product_service.exception.ProductNotFoundException;
import com.deepak.product_service.repository.ProductRepository;
import com.deepak.product_service.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setStock(productRequestDto.getStock());
        productRepository.save(product);
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getStock());
        
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

    @Override
    public List<ProductResponseDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getStock()))
                .toList();
    }

}
    

