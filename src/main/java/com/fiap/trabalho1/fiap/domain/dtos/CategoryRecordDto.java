package com.fiap.trabalho1.fiap.domain.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryRecordDto(@NotBlank String name, @NotBlank String type) {
}
