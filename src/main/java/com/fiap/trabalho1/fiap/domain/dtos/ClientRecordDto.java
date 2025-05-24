package com.fiap.trabalho1.fiap.domain.dtos;

import jakarta.validation.constraints.NotBlank;

public record ClientRecordDto(@NotBlank String name, @NotBlank String email, @NotBlank String CPF) {

}
