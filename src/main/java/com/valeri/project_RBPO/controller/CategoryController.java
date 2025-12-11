package com.valeri.project_RBPO.controller;

import com.valeri.project_RBPO.entity.Category;
import com.valeri.project_RBPO.service.CategoryService;
import com.valeri.project_RBPO.model.CategoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController
{
    private final CategoryService categoryService;

    // Получить все категории
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories()
    {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Получить категорию по ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id)
    {
        Category category = categoryService.getCategoryById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    // Создать новую категорию
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto)
    {
        try
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto));
        } catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Обновить категорию
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id, @RequestBody CategoryDto categoryDto)
    {
        Category category = categoryService.updateCategory(id, categoryDto);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    // Удалить категорию
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id)
    {
        return categoryService.deleteCategory(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Получить категорию по имени
    @GetMapping("/name/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }
}