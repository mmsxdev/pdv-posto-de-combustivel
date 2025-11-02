package com.br.pdvpostocombustivel.enums;

import org.springframework.security.core.GrantedAuthority;

public enum TipoAcesso implements GrantedAuthority {

    ADMINISTRADOR("ROLE_ADMINISTRADOR", "Acesso Administrador"),
    GERENCIA("ROLE_GERENCIA", "Acesso Gerência"),
    FUNCIONARIO("ROLE_FUNCIONARIO", "Acesso Funcionário");

    private  final String descricao;
    private final String role;

    // isso é para o usuario poderr visualizar a descrição do tipo e não igual a maquina vê
    private TipoAcesso(String role, String descricao) {
        this.role = role;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String getAuthority() {
        return this.role; // Retorna o nome do papel para o Spring Security
    }
}
