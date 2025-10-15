package com.br.pdvpostocombustivel.api.estoque.dto;

import com.br.pdvpostocombustivel.enums.TipoEstoque;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EstoqueRequest(
        @NotNull TipoEstoque tipoEstoque,
        @NotNull @Positive BigDecimal quantidade,
        @NotNull String localTanque,
        @NotNull String localEndereco,
        @NotNull String loteFabricacao,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataValidade
        ) {
}
