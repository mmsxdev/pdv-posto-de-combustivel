package com.br.pdvpostocombustivel.api.abastecimento.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record FinalizarAbastecimentoRequest(
        @NotNull @Positive
        BigDecimal litros,

        @NotNull @Positive
        BigDecimal valor
) {
}