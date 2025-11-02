package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Estoque;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    /**
     * Encontra o primeiro lote de estoque para um dado produto que tenha quantidade suficiente,
     * ordenado pela data de validade (FIFO - o que vence primeiro, sai primeiro).
     */
    Optional<Estoque> findFirstByProdutoAndQuantidadeGreaterThanEqualOrderByDataValidadeAsc(Produto produto, BigDecimal quantidade);
}
