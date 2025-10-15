package com.br.pdvpostocombustivel.api.produto.dto;

import com.br.pdvpostocombustivel.enums.TipoProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutoRequest(
        @NotBlank @Size(max = 100)
        String nome,
        @NotBlank @Size(max = 50)
        String referencia,
        @NotBlank @Size(max = 100)
        String fornecedor,
        @NotBlank @Size(max = 50)
        String marca,
        @NotNull
        TipoProduto categoria
) {
}