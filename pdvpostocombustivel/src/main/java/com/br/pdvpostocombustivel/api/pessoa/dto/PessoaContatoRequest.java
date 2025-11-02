package com.br.pdvpostocombustivel.api.pessoa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PessoaContatoRequest(
        @NotBlank @Size(min = 10, max = 15)
        String telefone,

        @NotBlank @Email @Size(max = 50)
        String email,

        @NotBlank @Size(max = 100)
        String endereco
) {
}