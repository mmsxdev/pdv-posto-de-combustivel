package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Venda;
import com.br.pdvpostocombustivel.enums.SituacaoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findAllByDataHoraBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Venda> findAllByDataHoraBetweenAndSituacaoVenda(LocalDateTime dataInicio, LocalDateTime dataFim, SituacaoVenda situacao);

    List<Venda> findAllBySituacaoVenda(SituacaoVenda situacaoVenda);

    @Query("SELECT COUNT(DISTINCT v.cliente.id) FROM Venda v WHERE v.dataHora BETWEEN :dataInicio AND :dataFim AND v.situacaoVenda = :situacao AND v.cliente IS NOT NULL")
    long countDistinctClientesByPeriodo(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim, @Param("situacao") SituacaoVenda situacao);
}