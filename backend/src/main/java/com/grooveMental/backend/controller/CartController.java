package com.grooveMental.backend.controller;

import com.grooveMental.backend.dto.ApiResponseDto;
import com.grooveMental.backend.dto.CartResponseDto;
import com.grooveMental.backend.model.User;
import com.grooveMental.backend.repository.UserRepo;
import com.grooveMental.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    UserRepo userRepo;

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepo.findByUsername(username);

        CartResponseDto cartResponse = cartService.findCartByUser(user);

        return ResponseEntity.ok(cartResponse);
    }

}