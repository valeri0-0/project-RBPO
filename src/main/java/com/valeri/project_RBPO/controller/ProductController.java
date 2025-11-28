package com.valeri.project_RBPO.controller;

import com.valeri.project_RBPO.entity.Product;
import com.valeri.project_RBPO.service.ProductService;
import com.valeri.project_RBPO.model.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController
{
    private final ProductService productService;

    // Получить все товары
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts()
    {
        return ResponseEntity.ok(productService.getAllProducts());
    }

     // Получить товар по ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id)
    {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    // Создать новый товар
    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductDto productDto)
    {
        try {
            Product product = productService.createProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Обновить товар
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable UUID id, @RequestBody ProductDto productDto)
    {
        try {
            Product product = productService.updateProduct(id, productDto);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Удалить товар
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id)
    {
        return productService.deleteProduct(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

     // Получить товары по категории
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable UUID categoryId)
    {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    // Поиск товаров по названию
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchProducts(name));
    }
}