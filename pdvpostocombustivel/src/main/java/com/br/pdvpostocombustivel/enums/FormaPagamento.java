package com.br.pdvpostocombustivel.enums;

public enum FormaPagamento {
    DINHEIRO("Dinheiro"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    PIX("PIX"),
    A_PRAZO("A Prazo / Faturado");

    private final String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() { return descricao; }
}