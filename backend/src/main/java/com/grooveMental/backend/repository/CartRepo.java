package com.grooveMental.backend.repository;

import com.grooveMental.backend.model.Cart;
import com.grooveMental.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}