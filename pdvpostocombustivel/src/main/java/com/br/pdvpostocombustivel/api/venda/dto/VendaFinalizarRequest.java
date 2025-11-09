package com.br.pdvpostocombustivel.api.venda.dto;

import com.br.pdvpostocombustivel.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;

public record VendaFinalizarRequest(
        @NotNull
        FormaPagamento formaPagamento
) {}