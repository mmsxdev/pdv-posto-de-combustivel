package com.br.pdvpostocombustivel.api.abastecimento.mapper;

import com.br.pdvpostocombustivel.api.abastecimento.dto.AbastecimentoResponse;
import com.br.pdvpostocombustivel.domain.entity.Abastecimento;
import org.springframework.stereotype.Component;

@Component
public class AbastecimentoMapper {

    public AbastecimentoResponse toResponse(Abastecimento entity) {
        if (entity == null) {
            return null;
        }

        return AbastecimentoResponse.builder()
                .id(entity.getId())
                .pumpNumber(entity.getNumeroBomba())
                .track(entity.getPista())
                .status(entity.getStatus())
                .vehiclePlate(entity.getPlacaVeiculo())
                .fuelType(entity.getProduto() != null ? entity.getProduto().getNome() : null)
                .produtoId(entity.getProduto() != null ? entity.getProduto().getId() : null)
                .liters(entity.getLitros())
                .value(entity.getValor())
                .durationMinutes(entity.getDuracaoMinutos())
                .frentistaId(entity.getFrentista() != null ? entity.getFrentista().getId() : null)
                .nomeFrentista(entity.getFrentista() != null ? entity.getFrentista().getNomeCompleto() : null)
                .build();
    }
}