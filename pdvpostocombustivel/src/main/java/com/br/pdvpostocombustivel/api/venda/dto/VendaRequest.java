package com.br.pdvpostocombustivel.api.venda.dto;

import com.br.pdvpostocombustivel.enums.FormaPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VendaRequest(
        Long clienteId, // Pode ser nulo para consumidor não identificado

        @NotNull
        FormaPagamento formaPagamento,

        @NotEmpty @Valid
        List<ItemVendaRequest> itens
) {}