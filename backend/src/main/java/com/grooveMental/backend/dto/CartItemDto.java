package com.grooveMental.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long id;
    private ClotheDto clothe;
    private int quantity;
    private BigDecimal price;
}