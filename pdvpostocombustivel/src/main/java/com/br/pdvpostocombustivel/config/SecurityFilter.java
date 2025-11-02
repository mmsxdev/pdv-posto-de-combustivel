package com.br.pdvpostocombustivel.config;

import com.br.pdvpostocombustivel.domain.repository.PessoaRepository;
import com.br.pdvpostocombustivel.config.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info(">>> [SecurityFilter] Iniciando filtro para a requisição: {} {}", request.getMethod(), request.getRequestURI());

        var token = this.recoverToken(request);
        if (token != null) {
            logger.info(">>> [SecurityFilter] Token encontrado na requisição.");
            var username = tokenService.validateToken(token);
            if (username != null) {
                logger.info(">>> [SecurityFilter] Token válido para o usuário: {}", username);
                pessoaRepository.findByAcessoUsuario(username).ifPresent(pessoa -> {
                    logger.info(">>> [SecurityFilter] Usuário '{}' encontrado no banco de dados.", username);

                    // Informa ao Spring que o usuário está autenticado
                    var authorities = Collections.singletonList(pessoa.getAcesso().getTipoAcesso());
                    logger.info(">>> [SecurityFilter] PERMISSÕES (Authorities) encontradas para o usuário '{}': {}", username, authorities);

                    var authentication = new UsernamePasswordAuthenticationToken(pessoa, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info(">>> [SecurityFilter] Usuário '{}' autenticado com sucesso no contexto de segurança.", username);
                });
            } else {
                logger.warn(">>> [SecurityFilter] Token recebido é INVÁLIDO ou EXPIRADO.");
            }
        } else {
            logger.info(">>> [SecurityFilter] Nenhum token JWT encontrado no cabeçalho 'Authorization'.");
        }
        // Continua a cadeia de filtros
        logger.info(">>> [SecurityFilter] Encaminhando requisição para o próximo filtro na cadeia.");
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7); // Remove "Bearer "
    }
}