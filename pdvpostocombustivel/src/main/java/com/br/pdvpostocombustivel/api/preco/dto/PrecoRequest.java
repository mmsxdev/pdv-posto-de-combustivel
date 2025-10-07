package com.br.pdvpostocombustivel.api.preco.dto;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrecoRequest(
        BigDecimal valor,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataAlteracao,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate horaAlteracao
) {
}
