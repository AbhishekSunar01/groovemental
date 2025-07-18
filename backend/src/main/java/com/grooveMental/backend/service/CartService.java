package com.grooveMental.backend.service;

import com.grooveMental.backend.dto.CartItemResponseDto;
import com.grooveMental.backend.dto.CartResponseDto;
import com.grooveMental.backend.model.Cart;
import com.grooveMental.backend.model.User;
import com.grooveMental.backend.repository.CartItemRepo;
import com.grooveMental.backend.repository.CartRepo;
import com.grooveMental.backend.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    UserRepo userRepo;
    CartRepo cartRepo;
    CartItemRepo cartItemRepo;

    public CartService(
            UserRepo userRepo,
            CartRepo cartRepo,
            CartItemRepo cartItemRepo
    ) {
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }

    public CartResponseDto findCartByUser(User user) {
        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartResponseDto dto = new CartResponseDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());

        List<CartItemResponseDto> cartItemDtos = cart.getCartItems().stream().map(item -> {
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

        dto.setCartItems(cartItemDtos);

        return dto;
    }

}