package com.grooveMental.backend.dto;

import java.math.BigDecimal;

public class ClotheDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    // no seller clothes list or seller details to avoid cycles
}
