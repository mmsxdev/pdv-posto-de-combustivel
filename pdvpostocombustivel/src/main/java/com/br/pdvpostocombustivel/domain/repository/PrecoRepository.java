package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PrecoRepository extends JpaRepository<Preco, Long> {
    Optional<Preco> findByProdutoAndDataVigencia(Produto produto, LocalDate dataVigencia);

    /**
     * Encontra o preço mais recente para um produto, com base na data fornecida.
     * Busca o primeiro preço cuja data de vigência seja menor ou igual à data atual, ordenando pela data de vigência de forma decrescente.
     */
    Optional<Preco> findFirstByProdutoAndDataVigenciaLessThanEqualOrderByDataVigenciaDesc(Produto produto, LocalDate data);
}