package com.br.pdvpostocombustivel.domain.entity;

import com.br.pdvpostocombustivel.enums.TipoEstoque;
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
                @UniqueConstraint(name = "uk_preco_tipo_data", columnNames = {"tipo_estoque", "data_vigencia"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Preco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_estoque", nullable = false, length = 30)
    private TipoEstoque tipoEstoque;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 3) // Preços de combustível costumam ter 3 casas decimais
    private BigDecimal valor;

    @NotNull
    @Column(name = "data_vigencia", nullable = false)
    private LocalDate dataVigencia;
}