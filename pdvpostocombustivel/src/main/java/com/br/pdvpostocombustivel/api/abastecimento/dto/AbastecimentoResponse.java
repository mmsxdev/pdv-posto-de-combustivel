package com.br.pdvpostocombustivel.api.abastecimento.dto;

import com.br.pdvpostocombustivel.domain.enums.StatusAbastecimento;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AbastecimentoResponse(
        Long id,
        String pumpNumber,
        String track,
        StatusAbastecimento status,
        String vehiclePlate,
        String fuelType,
        Long produtoId,
        BigDecimal liters,
        BigDecimal value,
        Integer durationMinutes,
        Long frentistaId,
        String nomeFrentista
) {
}