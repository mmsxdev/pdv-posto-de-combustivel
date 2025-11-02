package com.br.pdvpostocombustivel.api.pessoa.dto;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivel.api.contato.dto.ContatoResponse;
import com.br.pdvpostocombustivel.enums.TipoPessoa;

import java.time.LocalDate;

// Para resposta
public record PessoaResponse(
        Long id,
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        LocalDate dataNascimento,
        TipoPessoa tipoPessoa,
        ContatoResponse contato,
        AcessoResponse acesso
){}