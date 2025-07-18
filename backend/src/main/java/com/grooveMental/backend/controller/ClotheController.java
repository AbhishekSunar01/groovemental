package com.grooveMental.backend.controller;

import com.grooveMental.backend.dto.ClotheRequestDto;
import com.grooveMental.backend.dto.ClotheResponseDto;
import com.grooveMental.backend.service.ClotheService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clothes")
public class ClotheController {

    private final ClotheService clotheService;

    public ClotheController(ClotheService clotheService) {
        this.clotheService = clotheService;
    }

    @PostMapping("/add")
    public ResponseEntity<ClotheResponseDto> addClothe(
            @RequestBody ClotheRequestDto dto,
            Authentication authentication
    ) {
        String sellerUsername = authentication.getName();
        ClotheResponseDto created = clotheService.addClothe(dto, sellerUsername);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ClotheResponseDto>> getAll() {
        return ResponseEntity.ok(clotheService.getAll());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ClotheResponseDto>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(clotheService.getByCategory(categoryId));
    }
}
