package com.valeri.project_RBPO.repository;

import com.valeri.project_RBPO.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository
        extends JpaRepository<Category, UUID>
{
    Category findByName(String Name);
}
