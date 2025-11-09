package com.br.pdvpostocombustivel.domain.enums;

public enum StatusAbastecimento {
    ACTIVE,      // Em andamento
    AVAILABLE,   // Disponível para um novo abastecimento
    PAUSED,      // Pausado pelo frentista
    FINISHED,    // Finalizado, aguardando pagamento/faturamento
    INACTIVE     // Bomba desativada para manutenção, etc.
}