package com.grooveMental.backend.controller;

import com.grooveMental.backend.dto.ApiResponseDto;
import com.grooveMental.backend.dto.CategoryDto;
import com.grooveMental.backend.dto.CategoryResponseDto;
import com.grooveMental.backend.model.Category;
import com.grooveMental.backend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<CategoryDto>> createCategory(@RequestBody Category category) {
        CategoryDto saved = categoryService.createCategory(category);
        return ResponseEntity.ok(
                new ApiResponseDto<>(true, "Category created successfully", saved)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<CategoryResponseDto>>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(
                new ApiResponseDto<>(true, "Category list fetched", categories)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<CategoryDto>> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(new ApiResponseDto<>(true, "Category found", category));
        } else {
            return ResponseEntity.status(404).body(
                    new ApiResponseDto<>(false, "Category not found", null)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
                new ApiResponseDto<>(true, "Category deleted", null)
        );
    }
}
