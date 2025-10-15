package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.enums.TipoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PrecoRepository extends JpaRepository<Preco, Long> {
    Optional<Preco> findByTipoEstoqueAndDataVigencia(TipoEstoque tipoEstoque, LocalDate dataVigencia);
}