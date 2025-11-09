package com.br.pdvpostocombustivel.api.dashboard.dto;

import java.math.BigDecimal;

public record DashboardMetricasResponse(
        BigDecimal totalVendidoDia,
        long clientesAtendidosDia
) {
}