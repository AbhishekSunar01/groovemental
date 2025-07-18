package com.grooveMental.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponseDto {
    private Long id;
    private int quantity;
    private BigDecimal price;
    private ClotheDto clothe;

    @Data
    public static class ClotheDto {
        private Long id;
        private String name;
        private BigDecimal price;
        private String description;
    }
}

