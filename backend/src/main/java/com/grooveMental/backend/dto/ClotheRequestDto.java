package com.grooveMental.backend.dto;

import com.grooveMental.backend.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ClotheRequestDto {
    private String name;
    private BigDecimal price;
    private String description;
    private Long categoryId;
    private List<Image> images;
}
