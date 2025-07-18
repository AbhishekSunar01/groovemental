package com.grooveMental.backend.service;

import com.grooveMental.backend.dto.ClotheRequestDto;
import com.grooveMental.backend.dto.ClotheResponseDto;
import com.grooveMental.backend.model.Category;
import com.grooveMental.backend.model.Clothe;
import com.grooveMental.backend.model.User;
import com.grooveMental.backend.repository.CategoryRepo;
import com.grooveMental.backend.repository.ClotheRepo;
import com.grooveMental.backend.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClotheService {
    private final UserRepo userRepo;
    private final ClotheRepo clotheRepo;
    private final CategoryRepo categoryRepo;

    public ClotheService(
            UserRepo userRepo,
            ClotheRepo clotheRepo,
            CategoryRepo categoryRepo
    ) {
        this.userRepo = userRepo;
        this.clotheRepo = clotheRepo;
        this.categoryRepo = categoryRepo;
    }

    public ClotheResponseDto addClothe(ClotheRequestDto dto, String sellerUsername) {
        Category category = (Category) categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        User seller = userRepo.findByUsername(sellerUsername);
        if (seller == null) throw new EntityNotFoundException("Seller not found");

        Clothe clothe = new Clothe();
        clothe.setName(dto.getName());
        clothe.setPrice(dto.getPrice());
        clothe.setDescription(dto.getDescription());
        clothe.setCategory(category);
        clothe.setSeller(seller);

        Clothe saved = clotheRepo.save(clothe);
        return mapToDto(saved);
    }

    public List<ClotheResponseDto> getAll() {
        return clotheRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<ClotheResponseDto> getByCategory(Long categoryId) {
        return clotheRepo.findByCategory_Id(categoryId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ClotheResponseDto mapToDto(Clothe clothe) {
        ClotheResponseDto dto = new ClotheResponseDto();
        dto.setId(clothe.getId());
        dto.setName(clothe.getName());
        dto.setPrice(clothe.getPrice());
        dto.setDescription(clothe.getDescription());
        dto.setCategory(clothe.getCategory().getName());
        dto.setSeller(clothe.getSeller().getUsername());
        return dto;
    }


}
