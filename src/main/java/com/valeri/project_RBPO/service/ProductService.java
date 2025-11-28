package com.valeri.project_RBPO.service;

import com.valeri.project_RBPO.entity.Product;
import org.springframework.stereotype.Service;
import com.valeri.project_RBPO.entity.Category;
import com.valeri.project_RBPO.model.ProductDto;
import com.valeri.project_RBPO.repository.CategoryRepository;
import com.valeri.project_RBPO.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService
{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(UUID id)
    {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(ProductDto productDto)
    {
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() ->
                new RuntimeException("Категория не найдена с ID: " + productDto.getCategoryId()));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, ProductDto productDto)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар не найден с ID: " + id));
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категория не найдена с ID: " + productDto.getCategoryId()));

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public boolean deleteProduct(UUID id)
    {
        if (productRepository.existsById(id))
        {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(UUID categoryId)
    {
        return productRepository.findByCategoryId(categoryId);
    }

    @Transactional(readOnly = true)
    public List<Product> searchProducts(String name)
    {
        return productRepository.findByNameContaining(name);
    }
}