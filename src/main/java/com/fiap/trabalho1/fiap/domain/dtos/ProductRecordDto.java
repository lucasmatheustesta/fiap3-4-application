package com.fiap.trabalho1.fiap.domain.dtos;

import com.fiap.trabalho1.fiap.domain.CategoryModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value, @NotBlank String description, @NotNull CategoryModel category) {
}