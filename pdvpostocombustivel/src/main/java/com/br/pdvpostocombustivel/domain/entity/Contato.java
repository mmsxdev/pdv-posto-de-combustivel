package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //atributos

    @NotBlank
    @Size(min = 10, max = 15) // Ex: (XX) XXXXX-XXXX
    @Column(length = 15, nullable = false)
    private String telefone;

    @NotBlank
    @Email(message = "Formato de e-mail inválido")
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String email;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String endereco;

    // Construtores
    public Contato() {
    }

    public Contato(String telefone, String email, String endereco) {
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    // Setters
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}