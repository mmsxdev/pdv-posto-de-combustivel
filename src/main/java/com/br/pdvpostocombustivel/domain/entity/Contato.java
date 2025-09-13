package com.br.pdvpostocombustivel.domain.entity;

public class Contato {
    //atributos
    private String telefone;
    private String endereco;
    private  String email;

    //construtor
    public Contato (String telefone, String email, String endereco){
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    //getters
    public String getTelefone (){
        return telefone;
    }

    public String getEmail (){
        return email;
    }

    public String getEndereco (){
        return endereco;
    }

    //setters
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
