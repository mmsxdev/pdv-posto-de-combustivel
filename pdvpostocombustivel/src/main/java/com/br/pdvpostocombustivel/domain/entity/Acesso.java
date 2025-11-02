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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "acesso",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_acesso_usuario", columnNames = "usuario")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public Acesso(String usuario, String senha, com.br.pdvpostocombustivel.enums.TipoAcesso tipoAcesso) {
        this.usuario = usuario;
        this.senha =senha;
        this.tipoAcesso = tipoAcesso;
    }
}
