package com.grooveMental.backend.dto;

import com.grooveMental.backend.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ClotheResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String category;
    private String seller;
    private List<ImageResponseDto> images;
}
