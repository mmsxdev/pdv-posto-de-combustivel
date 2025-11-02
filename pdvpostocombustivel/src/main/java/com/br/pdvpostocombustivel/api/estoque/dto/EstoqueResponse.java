package com.br.pdvpostocombustivel.api.estoque.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EstoqueResponse(
        Long id,
        Long produtoId,
        String nomeProduto,
        String referenciaProduto,
        BigDecimal quantidade,
        String localTanque,
        String localEndereco,
        String loteFabricacao,
        LocalDate dataValidade
) {
}
