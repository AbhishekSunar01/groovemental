package com.grooveMental.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`") // 'order' is a reserved SQL word
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    @OneToMany
    private List<CartItem> items;

    @ManyToOne
    private PaymentStatus paymentStatus;
}

