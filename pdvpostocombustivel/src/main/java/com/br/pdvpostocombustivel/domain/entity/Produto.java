package com.br.pdvpostocombustivel.domain.entity;

import com.br.pdvpostocombustivel.enums.TipoProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produto",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_produto_referencia", columnNames = "referencia")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //atributos
    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String nome;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false, unique = true)
    private String referencia;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String fornecedor;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String marca;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private TipoProduto categoria;
}
