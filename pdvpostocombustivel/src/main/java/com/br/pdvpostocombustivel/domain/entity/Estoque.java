package com.br.pdvpostocombustivel.domain.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "estoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //atributos

    @Column(length = 10, nullable = false)
    private BigDecimal quantidade;

    @Column(length = 50, nullable = false)
    private String localTanque;

    @Column(length = 100, nullable = false)
    private String localEndereco;

    @Column(length = 10, nullable = false)
    private String loteFabricacao;

    @Column(length = 10, nullable = false)
    private LocalDate dataValidade;

    //construtor
    public Estoque() {
    }
    
    public  Estoque(BigDecimal quantidade, String localTanque, String localEndereco, String loteFabricacao,LocalDate dataValidade) {
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
