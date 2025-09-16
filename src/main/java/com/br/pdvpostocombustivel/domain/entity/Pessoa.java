package com.br.pdvpostocombustivel.domain.entity;
import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Past;

import java.time.LocalDate;


@Entity
@Table(name = "pessoa")
public class Pessoa {

    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String nomeCompleto;

    @Column(length = 14, nullable = false)
    private String cpfCnpj;

    @Column(length = 12)
    private Long numeroCtps;

    @Column(length = 10, nullable = false)
    private LocalDate dataNascimento;


    //construtor
    public Pessoa(String nomeCompleto, String cpfCnpj, LocalDate dataNascimento, Long numeroCtps, Long id) {
        this.nomeCompleto =  nomeCompleto;
        this.cpfCnpj = cpfCnpj;
        this.dataNascimento = dataNascimento;
        this.numeroCtps = numeroCtps;
        this.id = id;
    }

    public Pessoa() {

    }

    //getters
    public String getNomeCompleto() {
        return nomeCompleto;
    }
    public String getCpfCnpj() {
        return cpfCnpj;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public Long getNumeroCtps() {
        return numeroCtps;
    }
    public Long getId() {
        return id;
    }

    //setters
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public void setNumeroCtps(Long numeroCtps) {
        this.numeroCtps = numeroCtps;
    }
    public void setId(Long id) {
        this.id = id;
    }
}