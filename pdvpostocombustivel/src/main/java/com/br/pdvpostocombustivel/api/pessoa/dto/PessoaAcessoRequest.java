package com.br.pdvpostocombustivel.api.pessoa.dto;

import com.br.pdvpostocombustivel.enums.TipoAcesso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PessoaAcessoRequest(
        @NotBlank @Size(min = 4, max = 30)
        String usuario,
        @Size(min = 6, max = 30) // Senha pode ser nula na atualização
        String senha,
        @NotNull
        TipoAcesso tipoAcesso
) {}