package com.br.pdvpostocombustivel.api.pessoa.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

//Para Entrada
public record PessoaRequest(String nomeCompleto, String cpfCnpj, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNascimento, Long numeroCtps) {
}