package com.grooveMental.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class PaymentStatus {
    @Id
    @GeneratedValue
    private Long id;

    private String status; // e.g., PENDING, PAID, FAILED

    @OneToMany(mappedBy = "paymentStatus")
    private List<Cart> carts;

    @OneToMany(mappedBy = "paymentStatus")
    private List<Order> orders;
}

