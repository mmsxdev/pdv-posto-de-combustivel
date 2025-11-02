package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "item_venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda venda;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantidade;

    @NotNull
    @Positive
    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 3)
    private BigDecimal precoUnitario;

    @NotNull
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
}