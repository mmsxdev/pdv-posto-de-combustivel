package com.br.pdvpostocombustivel.api.produto.dto;

import com.br.pdvpostocombustivel.enums.TipoProduto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        String referencia,
        String fornecedor,
        String marca,
        TipoProduto categoria,
        BigDecimal quantidadeMinima
) {
}