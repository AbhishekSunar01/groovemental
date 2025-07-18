package com.grooveMental.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponseDto {
    private Long id;
    private Long userId;
    private List<CartItemResponseDto> cartItems;
    // getters and setters
}

