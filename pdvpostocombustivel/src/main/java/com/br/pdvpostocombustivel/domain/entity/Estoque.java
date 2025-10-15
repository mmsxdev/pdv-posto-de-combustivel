package com.br.pdvpostocombustivel.domain.entity;
import com.br.pdvpostocombustivel.enums.TipoEstoque;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "estoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_estoque", nullable = false, length = 30)
    private TipoEstoque tipoEstoque;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantidade;

    @NotNull
    @Column(name = "local_tanque", length = 50, nullable = false)
    private String localTanque;

    @NotNull
    @Column(name = "local_endereco", length = 100, nullable = false)
    private String localEndereco;

    @NotNull
    @Column(name = "lote_fabricacao", length = 20, nullable = false)
    private String loteFabricacao;

    @NotNull
    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    //construtor
    public Estoque() {
    }
    
    public  Estoque(TipoEstoque tipoEstoque, BigDecimal quantidade, String localTanque, String localEndereco, String loteFabricacao,LocalDate dataValidade) {
        this.tipoEstoque = tipoEstoque;
        this.quantidade = quantidade;
        this.localTanque = localTanque;
        this.localEndereco = localEndereco;
        this.loteFabricacao = loteFabricacao;
        this.dataValidade = dataValidade;
    }

    //getters
    public Long getId() {
        return id;
    }

    public TipoEstoque getTipoEstoque() {
        return tipoEstoque;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }
    public String getLocalTanque() {
        return localTanque;
    }
    public String getLocalEndereco() {
        return localEndereco;
    }
    public String getLoteFabricacao() {
        return loteFabricacao;
    }
    public LocalDate getDataValidade(){
        return dataValidade;
    }

    //setters
    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }
    public void setTipoEstoque(TipoEstoque tipoEstoque) {
        this.tipoEstoque = tipoEstoque;
    }
    public void setLocalEndereco(String localEndereco) {
        this.localEndereco = localEndereco;
    }
    public void setLocalTanque(String localTanque) {
        this.localTanque = localTanque;
    }
    public void setLoteFabricacao(String loteFabricacao) {
        this.loteFabricacao = loteFabricacao;
    }
    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

}
