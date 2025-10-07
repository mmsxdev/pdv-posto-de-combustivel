package com.br.pdvpostocombustivel.api.estoque.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EstoqueResponse(
        BigDecimal quantidade,
        String localTannque,
        String localEndereco,
        String localFabricacao,
        LocalDate dataValidade
) {
}
