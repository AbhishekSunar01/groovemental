package com.grooveMental.backend.service;

import com.grooveMental.backend.dto.ClotheRequestDto;
import com.grooveMental.backend.dto.ClotheResponseDto;
import com.grooveMental.backend.dto.ImageResponseDto;
import com.grooveMental.backend.model.Category;
import com.grooveMental.backend.model.Clothe;
import com.grooveMental.backend.model.Image;
import com.grooveMental.backend.model.User;
import com.grooveMental.backend.repository.CategoryRepo;
import com.grooveMental.backend.repository.ClotheRepo;
import com.grooveMental.backend.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    public void saveClothe(String name, BigDecimal price, String description,
                           Long categoryId, List<MultipartFile> images, User seller) {
        Clothe clothe = new Clothe();
        clothe.setName(name);
        clothe.setPrice(price);
        clothe.setDescription(description);
        clothe.setSeller(seller);

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        clothe.setCategory(category);

        List<Image> imageList = new ArrayList<>();

        try {
            String uploadDir = "uploads/";
            for (MultipartFile file : images) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());

                Image img = new Image();
                img.setUrl("/images/" + fileName);
                img.setClothe(clothe);
                imageList.add(img);
            }
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }

        clothe.setImages(imageList);
        clotheRepo.save(clothe);
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

        // Map images
        List<ImageResponseDto> images = clothe.getImages().stream().map(image -> {
            ImageResponseDto imageDto = new ImageResponseDto();
            imageDto.setId(image.getId());
            imageDto.setUrl(image.getUrl());
            return imageDto;
        }).toList();

        dto.setImages(images);

        return dto;
    }



}
