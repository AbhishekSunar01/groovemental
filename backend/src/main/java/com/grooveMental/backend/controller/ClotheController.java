package com.grooveMental.backend.controller;

import com.grooveMental.backend.dto.ApiResponseDto;
import com.grooveMental.backend.dto.ClotheRequestDto;
import com.grooveMental.backend.dto.ClotheResponseDto;
import com.grooveMental.backend.model.User;
import com.grooveMental.backend.repository.UserRepo;
import com.grooveMental.backend.service.ClotheService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/clothes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClotheController {

    private final ClotheService clotheService;
    private final UserRepo userRepo;

    public ClotheController(ClotheService clotheService, UserRepo userRepo) {
        this.clotheService = clotheService;
        this.userRepo = userRepo;
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

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto<ClotheResponseDto>> addClothe(
            @RequestParam("name") String name,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("images") List<MultipartFile> images,
            Authentication authentication
    ) {
        String username = authentication.getName();
        User seller = userRepo.findByUsername(username);

        clotheService.saveClothe(name, price, description, categoryId, images, seller);
        return ResponseEntity.ok(new ApiResponseDto(true, "Clothe added successfully"));
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
