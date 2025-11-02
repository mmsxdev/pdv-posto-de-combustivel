package com.br.pdvpostocombustivel.api.venda.dto;

import com.br.pdvpostocombustivel.enums.FormaPagamento;
import com.br.pdvpostocombustivel.enums.SituacaoVenda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendaResponse(
        Long id,
        Integer numeroNota,
        LocalDateTime dataHora,
        BigDecimal valorTotal,
        FormaPagamento formaPagamento,
        SituacaoVenda situacaoVenda,
        Long clienteId,
        String nomeCliente,
        List<ItemVendaResponse> itens
) {
}