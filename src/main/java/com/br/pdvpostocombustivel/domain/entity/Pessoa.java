package com.br.pdvpostocombustivel.domain.entity;
import java.util.Date;

public class Pessoa {

    //atributos
    private  String nomeCompleto;
    private String cpfCnpj;
    private Date dataNascimento;
    private Integer numeroCtps;

    //construtor
    public Pessoa(String nomeCompleto, String cpfCnpj, Date dataNascimento, int numeroCtps){
        this.nomeCompleto = nomeCompleto;
        this.cpfCnpj = cpfCnpj;
        this.dataNascimento = dataNascimento;
        this.numeroCtps = numeroCtps;
    }

    public Pessoa (){

    }

    //getters
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public int getNumeroCtps() {
        return numeroCtps;
    }

    //setters
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setNumeroCtps(Integer numeroCtps) {
        this.numeroCtps = numeroCtps;
    }

}
