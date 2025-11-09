package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Estoque;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Optional<Estoque> findByProdutoId(Long produtoId);

    // Busca o primeiro lote de estoque para um dado produto que tenha quantidade suficiente, ordenando pelo mais antigo (FIFO).
    Optional<Estoque> findFirstByProdutoAndQuantidadeGreaterThanEqualOrderByDataValidadeAsc(Produto produto, BigDecimal quantidade);

    @Query("SELECT count(e) FROM Estoque e WHERE e.quantidade <= e.produto.quantidadeMinima")
    long countByQuantidadeLessThanEqualProdutoQuantidadeMinima();
}