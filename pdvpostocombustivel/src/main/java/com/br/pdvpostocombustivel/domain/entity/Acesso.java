package com.br.pdvpostocombustivel.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "acesso",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_acesso_usuario", columnNames = "usuario")
        })
public class Acesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //atributos

    @NotBlank
    @Size(min = 4, max = 30)
    @Column(length = 30, nullable = false, unique = true)
    private String usuario;

    @NotBlank
    @Size(min = 6, max = 100) // O hash da senha ocupa mais espaço
    @Column(length = 100, nullable = false)
    private String senha;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acesso", nullable = false, length = 20)
    private com.br.pdvpostocombustivel.enums.TipoAcesso tipoAcesso;

    //contrutor
    public Acesso() {
    }
    
    public Acesso(String usuario, String senha, com.br.pdvpostocombustivel.enums.TipoAcesso tipoAcesso) {
        this.usuario = usuario;
        this.senha =senha;
        this.tipoAcesso = tipoAcesso;
    }

    //getters
    public Long getId() {
        return id;
    }
    
    public String getSenha() {
        return senha;
    }
    public String getUsuario() {
        return usuario;
    }

    public com.br.pdvpostocombustivel.enums.TipoAcesso getTipoAcesso() {
        return tipoAcesso;
    }

    //setters
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setTipoAcesso(com.br.pdvpostocombustivel.enums.TipoAcesso tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }
}
