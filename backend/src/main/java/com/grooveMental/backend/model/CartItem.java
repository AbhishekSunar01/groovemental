package com.grooveMental.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Clothe clothe;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private User user;

    private int quantity;
    private BigDecimal price;
}
