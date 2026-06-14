package com.deepak.order_service.dto;

import lombok.Data;

@Data
public class OrderRequestDto {
    private Long productId;
    private Integer quantity;
}
