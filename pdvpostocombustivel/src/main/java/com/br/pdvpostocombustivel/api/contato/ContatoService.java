package com.br.pdvpostocombustivel.api.contato;

import com.br.pdvpostocombustivel.api.contato.dto.ContatoRequest;
import com.br.pdvpostocombustivel.api.contato.dto.ContatoResponse;
import com.br.pdvpostocombustivel.domain.entity.Contato;
import com.br.pdvpostocombustivel.domain.repository.ContatoRepository;
import com.br.pdvpostocombustivel.exception.ContatoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContatoService {

    private final ContatoRepository contatoRepository;

    @Transactional(readOnly = true)
    public List<ContatoResponse> getAll() {
        return contatoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContatoResponse getById(Long id) {
        return contatoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ContatoException("Contato com ID " + id + " não encontrado."));
    }

    @Transactional
    public ContatoResponse save(ContatoRequest contatoRequest) {
        Contato contato = new Contato();
        fromRequest(contato, contatoRequest);
        return toResponse(contatoRepository.save(contato));
    }

    @Transactional
    public ContatoResponse update(Long id, ContatoRequest contatoRequest) {
        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new ContatoException("Contato com ID " + id + " não encontrado para atualização."));
        fromRequest(contato, contatoRequest);
        return toResponse(contatoRepository.save(contato));
    }

    @Transactional
    public void delete(Long id) {
        if (!contatoRepository.existsById(id)) {
            throw new ContatoException("Contato com ID " + id + " não encontrado para exclusão.");
        }
        contatoRepository.deleteById(id);
    }

    private ContatoResponse toResponse(Contato contato) {
        return new ContatoResponse(contato.getId(), contato.getTelefone(), contato.getEmail(), contato.getEndereco());
    }

    private void fromRequest(Contato contato, ContatoRequest request) {
        contato.setTelefone(request.telefone());
        contato.setEmail(request.email());
        contato.setEndereco(request.endereco());
    }
}