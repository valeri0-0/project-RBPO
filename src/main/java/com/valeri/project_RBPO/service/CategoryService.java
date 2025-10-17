package com.valeri.project_RBPO.service;

import com.valeri.project_RBPO.model.Category;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CategoryService {
    private List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    public Category getCategoryById(Long id) {
        return categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Category createCategory(Category category) {
        category.setId(nextId++);
        categories.add(category);
        return category;
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id);
        if (category != null) {
            category.setName(categoryDetails.getName());
        }
        return category;
    }

    public boolean deleteCategory(Long id) {
        return categories.removeIf(c -> c.getId().equals(id));
    }
}