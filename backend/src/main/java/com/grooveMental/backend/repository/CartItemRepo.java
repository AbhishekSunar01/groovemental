package com.grooveMental.backend.repository;

import com.grooveMental.backend.model.Cart;
import com.grooveMental.backend.model.CartItem;
import com.grooveMental.backend.model.Clothe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndClothe(Cart cart, Clothe clothe);
}