package com.br.pdvpostocombustivel.api.preco.dto;

import com.br.pdvpostocombustivel.enums.TipoEstoque;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrecoResponse(
        Long id,
        TipoEstoque tipoEstoque,
        BigDecimal valor,
        LocalDate dataVigencia
) {
}