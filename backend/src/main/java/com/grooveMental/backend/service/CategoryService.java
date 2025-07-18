package com.grooveMental.backend.service;

import com.grooveMental.backend.dto.CategoryDto;
import com.grooveMental.backend.model.Category;
import com.grooveMental.backend.repository.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public CategoryDto createCategory(Category category) {
        categoryRepo.save(category);
        return new CategoryDto(category.getId(), category.getName());
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepo.findAll().stream()
                .map(category -> new CategoryDto(category.getId(), category.getName()))
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
