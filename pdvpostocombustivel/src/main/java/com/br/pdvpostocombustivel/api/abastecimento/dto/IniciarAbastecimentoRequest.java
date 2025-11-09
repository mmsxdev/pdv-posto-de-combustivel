package com.br.pdvpostocombustivel.api.abastecimento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IniciarAbastecimentoRequest(
        @NotBlank(message = "A placa do veículo é obrigatória.") String placaVeiculo,
        @NotNull(message = "O ID do combustível é obrigatório.") Long produtoId,
        @NotNull(message = "O ID do frentista é obrigatório.") Long frentistaId
) {}