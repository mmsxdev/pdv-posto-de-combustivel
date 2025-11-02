package com.br.pdvpostocombustivel.api.auth;

import com.br.pdvpostocombustivel.api.auth.dto.LoginRequest;
import com.br.pdvpostocombustivel.api.auth.dto.LoginResponse;
import com.br.pdvpostocombustivel.config.TokenService;
import com.br.pdvpostocombustivel.domain.entity.Pessoa;
import com.br.pdvpostocombustivel.domain.repository.PessoaRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        logger.info("Tentativa de login para o usuário: {}", loginRequest.usuario());

        Pessoa pessoa = pessoaRepository.findByAcessoUsuario(loginRequest.usuario())
                 .orElse(null);

        if (pessoa == null) {
            logger.warn("Falha no login: Usuário '{}' não encontrado no banco de dados.", loginRequest.usuario());
            throw new BadCredentialsException("Usuário ou senha inválidos.");
        }

        logger.info("Usuário '{}' encontrado. ID da Pessoa: {}. Verificando senha...", loginRequest.usuario(), pessoa.getId());
            boolean senhaCorreta = passwordEncoder.matches(loginRequest.senha(), pessoa.getAcesso().getSenha());
            logger.info("A senha fornecida para o usuário '{}' está correta? {}", loginRequest.usuario(), senhaCorreta);

            if (!senhaCorreta) {
                logger.warn("Falha no login: Senha incorreta para o usuário '{}'.", loginRequest.usuario());
                throw new BadCredentialsException("Usuário ou senha inválidos.");
            }

            logger.info("Login bem-sucedido para o usuário '{}'. Gerando token...", loginRequest.usuario());
            String token = tokenService.generateToken(pessoa);

        return new LoginResponse(
                pessoa.getId(),
                pessoa.getNomeCompleto(),
                pessoa.getAcesso().getUsuario(),
                pessoa.getAcesso().getTipoAcesso(), token);
    }
}