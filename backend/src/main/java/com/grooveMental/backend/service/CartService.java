package com.grooveMental.backend.service;

import com.grooveMental.backend.dto.CartItemResponseDto;
import com.grooveMental.backend.dto.CartResponseDto;
import com.grooveMental.backend.model.*;
import com.grooveMental.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartService {

    private final UserRepo userRepo;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final ClotheRepo clotheRepo;

    public CartService(
            UserRepo userRepo,
            CartRepo cartRepo,
            CartItemRepo cartItemRepo,
            ClotheRepo clotheRepo
    ) {
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.clotheRepo = clotheRepo;
    }

    public CartResponseDto findCartByUser(User user) {
        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCartItems(new ArrayList<>());
                    return cartRepo.save(newCart);
                });

        return mapToDto(cart);
    }

    public CartResponseDto addToCart(User user, Long clotheId, int quantity) {
        Cart cart = getOrCreateCart(user);
        Clothe clothe = clotheRepo.findById(clotheId)
                .orElseThrow(() -> new RuntimeException("Clothe not found"));

        CartItem item = cartItemRepo.findByCartAndClothe(cart, clothe)
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new CartItem();
            item.setCart(cart);
            item.setClothe(clothe);
            item.setUser(user);
            item.setQuantity(quantity);
            item.setPrice(clothe.getPrice());
            cart.getCartItems().add(item);
        }

        cartItemRepo.save(item);
        return mapToDto(cart);
    }

    public CartResponseDto removeFromCart(User user, Long clotheId) {
        Cart cart = getOrCreateCart(user);
        Clothe clothe = clotheRepo.findById(clotheId)
                .orElseThrow(() -> new RuntimeException("Clothe not found"));

        CartItem item = cartItemRepo.findByCartAndClothe(cart, clothe)
                .orElse(null);

        if (item != null) {
            cart.getCartItems().remove(item);
            cartItemRepo.delete(item);
        }

        return mapToDto(cart);
    }

    public CartResponseDto updateCartItem(User user, Long clotheId, int quantity) {
        Cart cart = getOrCreateCart(user);
        Clothe clothe = clotheRepo.findById(clotheId)
                .orElseThrow(() -> new RuntimeException("Clothe not found"));

        CartItem item = cartItemRepo.findByCartAndClothe(cart, clothe)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (quantity <= 0) {
            cart.getCartItems().remove(item);
            cartItemRepo.delete(item);
        } else {
            item.setQuantity(quantity);
            cartItemRepo.save(item);
        }

        return mapToDto(cart);
    }

    public CartResponseDto clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        for (CartItem item : cart.getCartItems()) {
            cartItemRepo.delete(item);
        }
        cart.getCartItems().clear();
        return mapToDto(cartRepo.save(cart));
    }

    // ==================== HELPERS ====================

    private Cart getOrCreateCart(User user) {
        return cartRepo.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCartItems(new ArrayList<>());
                    return cartRepo.save(newCart);
                });
    }

    private CartResponseDto mapToDto(Cart cart) {
        CartResponseDto dto = new CartResponseDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());

        List<CartItemResponseDto> itemDtos = cart.getCartItems().stream().map(item -> {
            CartItemResponseDto itemDto = new CartItemResponseDto();
            itemDto.setId(item.getId());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());

            CartItemResponseDto.ClotheDto clotheDto = new CartItemResponseDto.ClotheDto();
            clotheDto.setId(item.getClothe().getId());
            clotheDto.setName(item.getClothe().getName());
            clotheDto.setPrice(item.getClothe().getPrice());
            clotheDto.setDescription(item.getClothe().getDescription());

            itemDto.setClothe(clotheDto);
            return itemDto;
        }).toList();

        dto.setCartItems(itemDtos);
        return dto;
    }
}
