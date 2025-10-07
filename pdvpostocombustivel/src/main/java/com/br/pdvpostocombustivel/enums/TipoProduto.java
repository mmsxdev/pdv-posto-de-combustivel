package com.br.pdvpostocombustivel.enums;

public enum TipoProduto {
    COMBUSTIVEL("Produto Combustível"),
    LUBRIFICANTE("Produto Lubrificante"),
    CONVENIENCIA("Produto Conveniência");

    private final String descricao;

    private TipoProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
