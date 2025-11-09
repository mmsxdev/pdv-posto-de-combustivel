package com.br.pdvpostocombustivel.domain.entity;

import com.br.pdvpostocombustivel.domain.enums.StatusAbastecimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "abastecimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Abastecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_bomba", nullable = false, length = 20)
    private String numeroBomba;

    @Column(name = "pista", nullable = false, length = 10)
    private String pista;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusAbastecimento status;

    @Column(name = "placa_veiculo", length = 15)
    private String placaVeiculo;

    // Muitos abastecimentos podem ser de um mesmo produto (combustível)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(precision = 10, scale = 3)
    private BigDecimal litros;

    @Column(precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(name = "inicio_abastecimento")
    private LocalDateTime inicioAbastecimento;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    // Muitos abastecimentos podem ser realizados por um mesmo frentista
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frentista_id")
    private Pessoa frentista;

    // Construtor para inicializar uma bomba como disponível
    public Abastecimento(String numeroBomba, String pista) {
        this.numeroBomba = numeroBomba;
        this.pista = pista;
        this.status = StatusAbastecimento.AVAILABLE;
        this.litros = BigDecimal.ZERO;
        this.valor = BigDecimal.ZERO;
        this.duracaoMinutos = 0;
    }
}