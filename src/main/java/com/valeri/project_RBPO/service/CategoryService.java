package com.valeri.project_RBPO.service;

import com.valeri.project_RBPO.entity.Category;
import org.springframework.stereotype.Service;
import com.valeri.project_RBPO.model.CategoryDto;
import com.valeri.project_RBPO.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(UUID id)
    {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category createCategory(CategoryDto categoryDto)
    {
        if (categoryRepository.findByName(categoryDto.getName()) != null)
        {
            throw new RuntimeException("Категория с названием '" + categoryDto.getName() + "' уже существует");
        }
        Category category = new Category();
        category.setName(categoryDto.getName());
        return categoryRepository.save(category);
    }

    public Category updateCategory(UUID id, CategoryDto categoryDto)
    {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setName(categoryDto.getName());
            return categoryRepository.save(category);
        }
        return null;
    }

    public boolean deleteCategory(UUID id)
    {
        if (categoryRepository.existsById(id))
        {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Category getCategoryByName(String name)
    {
        return categoryRepository.findByName(name);
    }
}