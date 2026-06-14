package com.deepak.order_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.deepak.order_service.dto.ProductResponseDto;
import com.deepak.order_service.exception.ProductServiceUnavailableException;


// @Component
// public class ProductClient {
//     @Autowired
//     private RestTemplate restTemplate;

//     public ProductResponseDto getProductById(Long id){
//         try {
//             String url = "http://localhost:8081/products/" + id;
//             return restTemplate.getForObject(url, ProductResponseDto.class);
//         } catch (ProductServiceUnavailableException e) {
//             throw new RuntimeException("Product service is unavailable");
//         }
       
//     }

    

// }

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponseDto getProductById(
        @PathVariable Long id
    );
}
