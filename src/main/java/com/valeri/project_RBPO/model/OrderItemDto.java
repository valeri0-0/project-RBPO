package com.valeri.project_RBPO.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.util.UUID;

@Data
public class OrderItemDto {
    @NotNull
    private UUID productId;

    @NotNull
    private UUID orderId;

    @NotNull
    @Positive
    private Integer quantity;
}
