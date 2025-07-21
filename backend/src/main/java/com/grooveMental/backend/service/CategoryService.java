package com.grooveMental.backend.service;

import com.grooveMental.backend.dto.CategoryDto;
import com.grooveMental.backend.dto.CategoryResponseDto;
import com.grooveMental.backend.model.Category;
import com.grooveMental.backend.repository.CartRepo;
import com.grooveMental.backend.repository.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final CartRepo cartRepo;

    public CategoryService(CategoryRepo categoryRepo, CartRepo cartRepo) {
        this.categoryRepo = categoryRepo;
        this.cartRepo = cartRepo;
    }

    public CategoryDto createCategory(Category category) {
        categoryRepo.save(category);
        return new CategoryDto(category.getId(), category.getName());
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepo.findAll().stream()
                .map(category -> new CategoryResponseDto(
                        category.getId(),
                        category.getName(),
                        category.getClothes() != null ? category.getClothes().size() : 0
                ))
                .toList();
    }

    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepo.findById(id).orElse(null);
        assert category != null;
        return new CategoryDto(category.getId(), category.getName());
    }

    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }
}
