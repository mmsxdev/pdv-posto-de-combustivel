package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivel.domain.entity.Acesso;
import com.br.pdvpostocombustivel.domain.repository.AcessoRepository;
import com.br.pdvpostocombustivel.exception.AcessoException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcessoService {

    private final AcessoRepository acessoRepository;
    private final PasswordEncoder passwordEncoder;

    public AcessoService(AcessoRepository acessoRepository, PasswordEncoder passwordEncoder) {
        this.acessoRepository = acessoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<AcessoResponse> getAll() {
        return acessoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AcessoResponse getById(Long id) {
        return acessoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new AcessoException("Acesso com ID " + id + " não encontrado."));
    }

    @Transactional
    public AcessoResponse save(AcessoRequest acessoRequest) {
        acessoRepository.findByUsuario(acessoRequest.usuario()).ifPresent(u -> {
            throw new AcessoException("Usuário '" + acessoRequest.usuario() + "' já existe.");
        });

        Acesso acesso = new Acesso();
        acesso.setUsuario(acessoRequest.usuario());
        acesso.setSenha(passwordEncoder.encode(acessoRequest.senha())); // Criptografando a senha
        acesso.setTipoAcesso(acessoRequest.tipoAcesso());

        return toResponse(acessoRepository.save(acesso));
    }

    @Transactional
    public AcessoResponse update(Long id, AcessoRequest acessoRequest) {
        Acesso acesso = acessoRepository.findById(id)
                .orElseThrow(() -> new AcessoException("Acesso com ID " + id + " não encontrado para atualização."));

        acesso.setUsuario(acessoRequest.usuario());
        acesso.setTipoAcesso(acessoRequest.tipoAcesso());
        // Apenas atualiza a senha se uma nova for fornecida
        if (acessoRequest.senha() != null && !acessoRequest.senha().isBlank()) {
            acesso.setSenha(passwordEncoder.encode(acessoRequest.senha()));
        }

        return toResponse(acessoRepository.save(acesso));
    }

    @Transactional
    public void delete(Long id) {
        if (!acessoRepository.existsById(id)) {
            throw new AcessoException("Acesso com ID " + id + " não encontrado para exclusão.");
        }
        acessoRepository.deleteById(id);
    }

    private AcessoResponse toResponse(Acesso acesso) {
        return new AcessoResponse(acesso.getId(), acesso.getUsuario(), acesso.getTipoAcesso());
    }
}