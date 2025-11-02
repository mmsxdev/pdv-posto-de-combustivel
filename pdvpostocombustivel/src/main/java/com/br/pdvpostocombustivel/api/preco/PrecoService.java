package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.domain.repository.PrecoRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
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
    private final ProdutoRepository produtoRepository;

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
        Produto produto = produtoRepository.findById(precoRequest.produtoId())
                .orElseThrow(() -> new PrecoException("Produto com ID " + precoRequest.produtoId() + " não encontrado."));

        precoRepository.findByProdutoAndDataVigencia(produto, precoRequest.dataVigencia())
                .ifPresent(p -> {
                    throw new PrecoException("Já existe um preço para o produto '" + produto.getNome() + "' na data " + precoRequest.dataVigencia());
                });

        Preco preco = new Preco();
        fromRequest(preco, produto, precoRequest);
        return toResponse(precoRepository.save(preco));
    }

    @Transactional
    public PrecoResponse update(Long id, PrecoRequest precoRequest) {
        Preco preco = precoRepository.findById(id)
                .orElseThrow(() -> new PrecoException("Preço com ID " + id + " não encontrado para atualização."));
        Produto produto = produtoRepository.findById(precoRequest.produtoId())
                .orElseThrow(() -> new PrecoException("Produto com ID " + precoRequest.produtoId() + " não encontrado."));

        fromRequest(preco, produto, precoRequest);
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
        return new PrecoResponse(preco.getId(),
                preco.getProduto().getId(),
                preco.getProduto().getNome(),
                preco.getProduto().getReferencia(),
                preco.getValor(),
                preco.getDataVigencia());
    }

    private void fromRequest(Preco preco, Produto produto, PrecoRequest request) {
        preco.setProduto(produto);
        preco.setValor(request.valor());
        preco.setDataVigencia(request.dataVigencia());
    }
}