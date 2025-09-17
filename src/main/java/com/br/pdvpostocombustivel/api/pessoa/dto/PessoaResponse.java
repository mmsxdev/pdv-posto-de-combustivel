package com.br.pdvpostocombustivel.api.pessoa.dto;

import java.time.LocalDate;

//Para Resposta
public record PessoaResponse(Long id, String nomeCompleto, String cpfCnpj, LocalDate dataNascimento, Long numeroCtps) {
}