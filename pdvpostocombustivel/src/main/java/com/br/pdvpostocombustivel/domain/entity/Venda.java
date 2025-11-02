package com.br.pdvpostocombustivel.domain.entity;

import com.br.pdvpostocombustivel.enums.FormaPagamento;
import com.br.pdvpostocombustivel.enums.SituacaoVenda;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_nota")
    private Integer numeroNota;

    @NotNull
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @NotNull
    @Column(name = "valor_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false, length = 20)
    private FormaPagamento formaPagamento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_venda", nullable = false, length = 20)
    private SituacaoVenda situacaoVenda;

    // Muitas vendas para um cliente. Pode ser nulo (consumidor não identificado).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Pessoa cliente;

    // Uma venda tem muitos itens.
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemVenda> itens = new ArrayList<>();
}