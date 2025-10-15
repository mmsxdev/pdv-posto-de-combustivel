package com.br.pdvpostocombustivel.api.produto;

import com.br.pdvpostocombustivel.api.produto.dto.ProdutoRequest;
import com.br.pdvpostocombustivel.api.produto.dto.ProdutoResponse;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import com.br.pdvpostocombustivel.exception.ProdutoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public List<ProdutoResponse> getAll() {
        return produtoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoResponse getById(Long id) {
        return produtoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ProdutoException("Produto com ID " + id + " não encontrado."));
    }

    @Transactional
    public ProdutoResponse save(ProdutoRequest produtoRequest) {
        produtoRepository.findByReferencia(produtoRequest.referencia()).ifPresent(p -> {
            throw new ProdutoException("Já existe um produto com a referência '" + produtoRequest.referencia() + "'.");
        });
        Produto produto = new Produto();
        fromRequest(produto, produtoRequest);
        return toResponse(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoResponse update(Long id, ProdutoRequest produtoRequest) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoException("Produto com ID " + id + " não encontrado para atualização."));

        fromRequest(produto, produtoRequest);
        return toResponse(produtoRepository.save(produto));
    }

    @Transactional
    public void delete(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoException("Produto com ID " + id + " não encontrado para exclusão.");
        }
        produtoRepository.deleteById(id);
    }

    private ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getReferencia(),
                produto.getFornecedor(), produto.getMarca(), produto.getCategoria());
    }

    private void fromRequest(Produto produto, ProdutoRequest request) {
        produto.setNome(request.nome());
        produto.setReferencia(request.referencia());
        produto.setFornecedor(request.fornecedor());
        produto.setMarca(request.marca());
        produto.setCategoria(request.categoria());
    }
}