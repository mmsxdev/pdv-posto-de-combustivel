package com.br.pdvpostocombustivel.api.pessoa.dto;

import com.br.pdvpostocombustivel.enums.TipoPessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

// Para Entrada
public record PessoaRequest(
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataNascimento,
        TipoPessoa tipoPessoa,

        @NotNull @Valid
        PessoaContatoRequest contato,

        @Valid // Opcional para clientes, mas obrigatório para funcionários/admins
        PessoaAcessoRequest acesso
)
{ }
