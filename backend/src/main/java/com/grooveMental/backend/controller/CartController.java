package com.grooveMental.backend.controller;

import com.grooveMental.backend.dto.ApiResponseDto;
import com.grooveMental.backend.dto.CartResponseDto;
import com.grooveMental.backend.model.User;
import com.grooveMental.backend.repository.UserRepo;
import com.grooveMental.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepo.findByUsername(username);
        CartResponseDto cartResponse = cartService.findCartByUser(user);
        return ResponseEntity.ok(cartResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addToCart(
            Authentication authentication,
            @RequestParam Long clotheId,
            @RequestParam int quantity) {

        String username = authentication.getName();
        User user = userRepo.findByUsername(username);

        CartResponseDto updatedCart = cartService.addToCart(user, clotheId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartResponseDto> removeFromCart(
            Authentication authentication,
            @RequestParam Long clotheId) {

        String username = authentication.getName();
        User user = userRepo.findByUsername(username);

        CartResponseDto updatedCart = cartService.removeFromCart(user, clotheId);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/update")
    public ResponseEntity<CartResponseDto> updateCartItem(
            Authentication authentication,
            @RequestParam Long clotheId,
            @RequestParam int quantity) {

        String username = authentication.getName();
        User user = userRepo.findByUsername(username);

        CartResponseDto updatedCart = cartService.updateCartItem(user, clotheId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartResponseDto> clearCart(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepo.findByUsername(username);

        CartResponseDto clearedCart = cartService.clearCart(user);
        return ResponseEntity.ok(clearedCart);
    }
}
