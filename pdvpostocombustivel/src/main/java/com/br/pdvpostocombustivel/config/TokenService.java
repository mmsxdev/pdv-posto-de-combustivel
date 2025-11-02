package com.br.pdvpostocombustivel.config;

import com.br.pdvpostocombustivel.domain.entity.Pessoa;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Pessoa pessoa) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Instant expirationTime = genExpirationDate();

        return Jwts.builder()
                .issuer("PDV Posto API")
                .subject(pessoa.getAcesso().getUsuario())
                .claim("nomeCompleto", pessoa.getNomeCompleto())
                .issuedAt(new Date())
                .expiration(Date.from(expirationTime))
                .signWith(key)
                .compact();
    }

    public String validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getSubject(); // Retorna o nome de usuário (subject)
        } catch (Exception e) {
            // Token inválido, expirado, etc.
            return null;
        }
    }

    /**
     * Gera uma data de expiração para o token.
     * Ex: 8 horas a partir de agora.
     */
    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }
}