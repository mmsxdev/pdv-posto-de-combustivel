package com.br.pdvpostocombustivel.enums;

import lombok.Getter;

@Getter
public enum TipoProduto {
    LUBRIFICANTE("Lubrificantes"),
    ADITIVO("Aditivos"),
    CONVENIENCIA("Conveniência"),
    AUTOMOTIVO("Peças e Acessórios Automotivos");

    private final String descricao;

    TipoProduto(String descricao) {
        this.descricao = descricao;
    }

}
