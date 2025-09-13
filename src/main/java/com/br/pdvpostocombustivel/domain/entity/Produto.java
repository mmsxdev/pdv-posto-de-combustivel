package com.br.pdvpostocombustivel.domain.entity;

public class Produto {
    //atributos
    private String nome;
    private String referencia;
    private String marca;
    private String categoria;
    private String fornecedor;

    //construtor
    public Produto (String nome, String referencia, String marca, String categoria, String fornecedor){
        this.categoria = categoria;
        this.nome = nome;
        this.fornecedor = fornecedor;
        this.marca = marca;
        this.referencia = referencia;
    }

    //getters
    public String getCategoria() {
        return categoria;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public String getMarca() {
        return marca;
    }

    public String getNome() {
        return nome;
    }

    public String getReferencia() {
        return referencia;
    }

    //setters
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
