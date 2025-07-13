package com.grooveMental.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User seller;

    @OneToMany(mappedBy = "instrument")
    private List<CartItem> cartItems;
}

