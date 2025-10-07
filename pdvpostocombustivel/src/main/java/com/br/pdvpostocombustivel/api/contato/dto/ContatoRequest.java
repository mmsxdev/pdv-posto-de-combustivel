package com.br.pdvpostocombustivel.api.contato.dto;

// Para Entrada
public record ContatoRequest(
        String telefone,
        String email,
        String endereco
)
{ }
