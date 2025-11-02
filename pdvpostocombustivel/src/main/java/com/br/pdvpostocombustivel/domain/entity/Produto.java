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

import java.util.ArrayList;
import java.util.List;

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

    // RELACIONAMENTO 1..*: Um Produto pode ter vários registros de Estoque.
    // mappedBy = "produto": Indica que a entidade Estoque é a dona do relacionamento (ela tem a foreign key).
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Estoque> estoques = new ArrayList<>();

    // RELACIONAMENTO 1..*: Um Produto pode ter um histórico de Preços.
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Preco> precos = new ArrayList<>();
}
