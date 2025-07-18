package com.grooveMental.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClotheRequestDto {
    private String name;
    private BigDecimal price;
    private String description;
    private Long categoryId;
}
