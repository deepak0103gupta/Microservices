package com.deepak.product_service.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.product_service.dto.ProductRequestDto;
import com.deepak.product_service.dto.ProductResponseDto;
import com.deepak.product_service.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
       
            ProductResponseDto productResponseDto = productService.createProduct(productRequestDto);
            return ResponseEntity.ok(productResponseDto);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id)  {
        ProductResponseDto p1 = productService.getProductById(id);
        if(p1!=null){
            return ResponseEntity.ok(p1);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        List<ProductResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

}
