package com.br.pdvpostocombustivel.api.preco.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrecoResponse(
        Long id,
        Long produtoId,
        String nomeProduto,
        String referenciaProduto,
        BigDecimal valor,
        LocalDate dataVigencia
) {
}