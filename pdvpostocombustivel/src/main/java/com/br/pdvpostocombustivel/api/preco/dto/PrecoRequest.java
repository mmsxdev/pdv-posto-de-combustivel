package com.br.pdvpostocombustivel.api.preco.dto;

import com.br.pdvpostocombustivel.enums.TipoEstoque;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrecoRequest(
        @NotNull TipoEstoque tipoEstoque,
        @NotNull @Positive BigDecimal valor,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataVigencia
) {
}