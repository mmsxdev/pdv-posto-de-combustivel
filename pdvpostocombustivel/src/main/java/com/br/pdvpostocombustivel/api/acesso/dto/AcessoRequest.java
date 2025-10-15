package com.br.pdvpostocombustivel.api.acesso.dto;

import com.br.pdvpostocombustivel.enums.TipoAcesso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AcessoRequest(
        @NotBlank @Size(min = 4, max = 30)
        String usuario,
        @NotBlank @Size(min = 6, max = 30)
        String senha,
        @NotNull
        TipoAcesso tipoAcesso
) {}