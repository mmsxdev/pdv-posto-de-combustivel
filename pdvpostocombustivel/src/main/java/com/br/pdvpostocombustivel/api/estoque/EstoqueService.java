package com.br.pdvpostocombustivel.api.estoque;


import com.br.pdvpostocombustivel.domain.entity.Estoque;
import com.br.pdvpostocombustivel.domain.repository.EstoqueRepository;
import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueRequest;
import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueResponse;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.entity.Estoque;
import com.br.pdvpostocombustivel.domain.repository.EstoqueRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import com.br.pdvpostocombustivel.exception.EstoqueException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, ProdutoRepository produtoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<EstoqueResponse> getAll() {
        return estoqueRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstoqueResponse getById(Long id) {
        return estoqueRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EstoqueException("Estoque com ID " + id + " não encontrado."));
    }

    @Transactional
    public EstoqueResponse save(EstoqueRequest estoqueRequest) {
        Estoque estoque = new Estoque();
        fromRequest(estoque, estoqueRequest);
        return toResponse(estoqueRepository.save(estoque));
    }

    @Transactional
    public EstoqueResponse update(Long id, EstoqueRequest estoqueRequest) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new EstoqueException("Registro de estoque com ID " + id + " não encontrado para atualização."));
        fromRequest(estoque, estoqueRequest);
        return toResponse(estoqueRepository.save(estoque));
    }

    @Transactional
    public void delete(Long id) {
        if (!estoqueRepository.existsById(id)) {
            throw new EstoqueException("Registro de estoque com ID " + id + " não encontrado para exclusão.");
        }
        estoqueRepository.deleteById(id);
    }

    @Transactional
    public void darBaixa(Long produtoId, BigDecimal quantidade) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto ID: " + produtoId));

        // Validação para garantir que há estoque suficiente
        if (estoque.getQuantidade().compareTo(quantidade) < 0) {
            throw new IllegalStateException(
                    String.format("Estoque insuficiente para o produto '%s'. Em estoque: %.3f, Requisitado: %.3f",
                            estoque.getProduto().getNome(), estoque.getQuantidade(), quantidade)
            );
        }

        estoque.setQuantidade(estoque.getQuantidade().subtract(quantidade));
        estoqueRepository.save(estoque);
    }

    private EstoqueResponse toResponse(Estoque estoque) {
        return new EstoqueResponse(
                estoque.getId(),
                estoque.getProduto().getId(),
                estoque.getProduto().getNome(),
                estoque.getProduto().getReferencia(),
                estoque.getQuantidade(),
                estoque.getLocalTanque(),
                estoque.getLocalEndereco(),
                estoque.getLoteFabricacao(),
                estoque.getDataValidade()
        );
    }

    private void fromRequest(Estoque estoque, EstoqueRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new EstoqueException("Produto com ID " + request.produtoId() + " não encontrado."));

        estoque.setProduto(produto);
        estoque.setQuantidade(request.quantidade());
        estoque.setLocalTanque(request.localTanque());
        estoque.setLocalEndereco(request.localEndereco());
        estoque.setLoteFabricacao(request.loteFabricacao());
        estoque.setDataValidade(request.dataValidade());
    }
}
