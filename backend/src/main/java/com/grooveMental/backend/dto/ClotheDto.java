package com.grooveMental.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClotheDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private List<String> imageUrls;
}
