package com.br.pdvpostocombustivel.api.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String usuario,
        @NotBlank
        String senha
) {
}