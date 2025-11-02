package com.br.pdvpostocombustivel.api.auth.dto;

import com.br.pdvpostocombustivel.enums.TipoAcesso;

public record LoginResponse(
        Long id,
        String nomeCompleto,
        String usuario,
        TipoAcesso tipoAcesso,
        String token
) {
}