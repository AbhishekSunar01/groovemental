package com.grooveMental.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClotheResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String category;
    private String seller;
}
