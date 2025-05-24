package com.fiap.trabalho1.fiap.domain.dtos;

import com.fiap.trabalho1.fiap.domain.ClientModel;
import com.fiap.trabalho1.fiap.domain.ProductModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record OrderRecordDto(@NotBlank List<ProductModel> products, @NotBlank ClientModel client) {
}