package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.domain.repository.PrecoRepository;
import com.br.pdvpostocombustivel.exception.PrecoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrecoService {

    private final PrecoRepository precoRepository;

    @Transactional(readOnly = true)
    public List<PrecoResponse> getAll() {
        return precoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PrecoResponse getById(Long id) {
        return precoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new PrecoException("Preço com ID " + id + " não encontrado."));
    }

    @Transactional
    public PrecoResponse save(PrecoRequest precoRequest) {
        precoRepository.findByTipoEstoqueAndDataVigencia(precoRequest.tipoEstoque(), precoRequest.dataVigencia())
                .ifPresent(p -> {
                    throw new PrecoException("Já existe um preço para " + precoRequest.tipoEstoque() + " na data " + precoRequest.dataVigencia());
                });

        Preco preco = new Preco();
        fromRequest(preco, precoRequest);
        return toResponse(precoRepository.save(preco));
    }

    @Transactional
    public PrecoResponse update(Long id, PrecoRequest precoRequest) {
        Preco preco = precoRepository.findById(id)
                .orElseThrow(() -> new PrecoException("Preço com ID " + id + " não encontrado para atualização."));
        fromRequest(preco, precoRequest);
        return toResponse(precoRepository.save(preco));
    }

    @Transactional
    public void delete(Long id) {
        if (!precoRepository.existsById(id)) {
            throw new PrecoException("Preço com ID " + id + " não encontrado para exclusão.");
        }
        precoRepository.deleteById(id);
    }

    private PrecoResponse toResponse(Preco preco) {
        return new PrecoResponse(preco.getId(), preco.getTipoEstoque(), preco.getValor(), preco.getDataVigencia());
    }

    private void fromRequest(Preco preco, PrecoRequest request) {
        preco.setTipoEstoque(request.tipoEstoque());
        preco.setValor(request.valor());
        preco.setDataVigencia(request.dataVigencia());
    }
}