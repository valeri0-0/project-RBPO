package com.valeri.project_RBPO.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerDto {
    @NotBlank
    private String name;

    @Email
    private String email;
}

