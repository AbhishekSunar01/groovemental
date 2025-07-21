package com.grooveMental.backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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
        private List<ImageDto> images;

        @Data
        public static class ImageDto {
            private Long id;
            private String url;
        }
    }
}
