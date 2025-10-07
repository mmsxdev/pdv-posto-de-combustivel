package com.br.pdvpostocombustivel.domain.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "custo")
public class Custo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //atributos

    @Column(length = 5, nullable = false)
    private Double imposto;

    @Column(length = 10, nullable = false)
    private Double custoVariavel;

    @Column(length = 10, nullable = false)
    private Double custoFixo;

    @Column(length = 5, nullable = false)
    private Double margemLucro;

    @Column(length = 10, nullable = false)
    private LocalDate dataProcessamento;

    //construtor
    public Custo() {
    }
    
    public Custo(Double imposto, Double custoVariavel, Double custoFixo, Double margemLucro, LocalDate dataProcessamento) {
        this.imposto = imposto;
        this.custoVariavel = custoVariavel;
        this.custoFixo = custoFixo;
        this.margemLucro = margemLucro;
        this.dataProcessamento = dataProcessamento;
    }

    //getters
    public Long getId() {
        return id;
    }
    
    public Double getCustoFixo() {
        return custoFixo;
    }
    public LocalDate getDataProcessamento() {
        return dataProcessamento;
    }
    public Double getCustoVariavel() {
        return custoVariavel;
    }
    public Double getImposto() {
        return imposto;
    }
    public Double getMargemLucro() {
        return margemLucro;
    }

    //setters
    public void setCustoFixo(Double custoFixo) {

    }
    public void setCustoVariavel(Double custoVariavel) {
        this.custoVariavel = custoVariavel;
    }
    public void setDataProcessamento(LocalDate dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }
    public void setImposto(Double imposto) {
        this.imposto = imposto;
    }
    public void setMargemLucro(Double margemLucro) {
        this.margemLucro = margemLucro;
    }
}
