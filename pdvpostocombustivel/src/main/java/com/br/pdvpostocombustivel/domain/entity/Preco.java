package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "preco",
        uniqueConstraints = {
                // Garante que só existe um preço por tipo de combustível para uma data de vigência
                @UniqueConstraint(name = "uk_preco_produto_data", columnNames = {"produto_id", "data_vigencia"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Preco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 3) // Preços de combustível costumam ter 3 casas decimais
    private BigDecimal valor;

    @NotNull
    @Column(name = "data_vigencia", nullable = false)
    private LocalDate dataVigencia;
}