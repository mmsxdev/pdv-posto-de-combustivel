package com.br.pdvpostocombustivel.api.venda.dto;

import java.math.BigDecimal;

public record ItemVendaResponse(
        Long id,
        Long produtoId,
        String nomeProduto,
        String referenciaProduto,
        BigDecimal quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {}