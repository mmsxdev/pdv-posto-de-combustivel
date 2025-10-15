package com.br.pdvpostocombustivel.api.estoque.dto;

import com.br.pdvpostocombustivel.enums.TipoEstoque;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EstoqueResponse(
        Long id,
        TipoEstoque tipoEstoque,
        BigDecimal quantidade,
        String localTanque,
        String localEndereco,
        String loteFabricacao,
        LocalDate dataValidade
) {
}
