package com.valeri.project_RBPO.repository;

import com.valeri.project_RBPO.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository
        extends  JpaRepository <Product, UUID> {
    List<Product> findByCategoryId (UUID categoryId);

    @Query("SELECT p FROM Product p WHERE p.name ILIKE %:name%")
    List<Product> findByNameContaining(String name);
}
