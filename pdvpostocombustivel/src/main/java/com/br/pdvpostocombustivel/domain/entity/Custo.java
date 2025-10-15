package com.br.pdvpostocombustivel.domain.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.br.pdvpostocombustivel.enums.TipoCusto;

@Entity
@Table(name = "custo")
public class Custo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_custo", nullable = false, length = 20)
    private TipoCusto tipoCusto;

    @NotNull
    @Column(name = "imposto", nullable = false, precision = 5, scale = 2)
    private BigDecimal imposto;

    @NotNull
    @Column(name = "custo_variavel", nullable = false, precision = 10, scale = 2)
    private BigDecimal custoVariavel;

    @NotNull
    @Column(name = "custo_fixo", nullable = false, precision = 10, scale = 2)
    private BigDecimal custoFixo;

    @NotNull
    @Column(name = "margem_lucro", nullable = false, precision = 5, scale = 2)
    private BigDecimal margemLucro;

    @NotNull
    @Column(name = "data_processamento", nullable = false)
    private LocalDate dataProcessamento;

    //construtor
    public Custo() {
    }
    
    public Custo(TipoCusto tipoCusto, BigDecimal imposto, BigDecimal custoVariavel, BigDecimal custoFixo, BigDecimal margemLucro, LocalDate dataProcessamento) {
        this.tipoCusto = tipoCusto;
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
    
    public TipoCusto getTipoCusto() {
        return tipoCusto;
    }

    public BigDecimal getCustoFixo() {
        return custoFixo;
    }
    public LocalDate getDataProcessamento() {
        return dataProcessamento;
    }
    public BigDecimal getCustoVariavel() {
        return custoVariavel;
    }
    public BigDecimal getImposto() {
        return imposto;
    }
    public BigDecimal getMargemLucro() {
        return margemLucro;
    }

    //setters
    public void setTipoCusto(TipoCusto tipoCusto) {
        this.tipoCusto = tipoCusto;
    }

    public void setCustoFixo(BigDecimal custoFixo) {
        this.custoFixo = custoFixo;
    }
    public void setCustoVariavel(BigDecimal custoVariavel) {
        this.custoVariavel = custoVariavel;
    }
    public void setDataProcessamento(LocalDate dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }
    public void setImposto(BigDecimal imposto) {
        this.imposto = imposto;
    }
    public void setMargemLucro(BigDecimal margemLucro) {
        this.margemLucro = margemLucro;
    }
}
