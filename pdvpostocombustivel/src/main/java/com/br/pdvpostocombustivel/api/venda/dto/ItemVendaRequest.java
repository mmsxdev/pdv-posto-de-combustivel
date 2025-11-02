package com.br.pdvpostocombustivel.api.venda.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemVendaRequest(
        @NotNull
        Long produtoId,

        @NotNull @Positive
        BigDecimal quantidade
) {}