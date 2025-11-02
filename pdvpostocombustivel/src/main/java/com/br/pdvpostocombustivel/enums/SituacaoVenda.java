package com.br.pdvpostocombustivel.enums;

public enum SituacaoVenda {
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada"),
    EM_ABERTO("Em Aberto");

    private final String descricao;

    SituacaoVenda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() { return descricao; }
}