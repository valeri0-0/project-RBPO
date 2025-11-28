package com.valeri.project_RBPO.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class ProductDto {
    @NotBlank
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private UUID categoryId;
}
