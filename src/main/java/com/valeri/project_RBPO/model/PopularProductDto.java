package com.valeri.project_RBPO.model;

import lombok.Data;

@Data
public class PopularProductDto
{
    private String productName;
    private Long totalSold;
    private Double totalRevenue;
}
