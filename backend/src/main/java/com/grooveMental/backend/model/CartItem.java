package com.grooveMental.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference(value = "clothe-cartitem")
    private Clothe clothe;

    @ManyToOne
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    private User user;

    private int quantity;
    private BigDecimal price;
}
