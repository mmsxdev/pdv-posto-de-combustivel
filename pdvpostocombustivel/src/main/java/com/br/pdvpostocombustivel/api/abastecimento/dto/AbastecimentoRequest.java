package com.br.pdvpostocombustivel.api.abastecimento.dto;

import com.br.pdvpostocombustivel.domain.enums.StatusAbastecimento;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AbastecimentoRequest {
    private Long id;
    private String pumpNumber;
    private String track;
    private StatusAbastecimento status;
    private String vehiclePlate;
    private String fuelType;
    private BigDecimal liters;
    private BigDecimal value;
    private Integer durationMinutes;
    private Long frentistaId;
    private String nomeFrentista;
}