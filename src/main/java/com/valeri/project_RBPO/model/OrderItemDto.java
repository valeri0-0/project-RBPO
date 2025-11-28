package com.valeri.project_RBPO.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class OrderItemDto {
    @NotNull
    private UUID productId;

    @NotNull
    private Integer quantity;
}
