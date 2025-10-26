package com.br.pdvpostocombustivel.enums;

public enum TipoCusto {
    ENERGIA("Custo de Energia"),
    FRETE("Custo de Frete"),
    AGUA("Custo de Água");


    private final String descricao;

    private TipoCusto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
