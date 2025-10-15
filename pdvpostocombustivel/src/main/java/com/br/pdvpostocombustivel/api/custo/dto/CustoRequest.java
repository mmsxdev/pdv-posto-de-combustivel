package com.br.pdvpostocombustivel.api.custo.dto;

import com.br.pdvpostocombustivel.enums.TipoCusto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CustoRequest(
        @NotNull TipoCusto tipoCusto,
        @NotNull @PositiveOrZero BigDecimal imposto,
        @NotNull @PositiveOrZero BigDecimal custoVariavel,
        @NotNull @PositiveOrZero BigDecimal custoFixo,
        @NotNull @PositiveOrZero BigDecimal margemLucro,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataProcessamento
) {
}