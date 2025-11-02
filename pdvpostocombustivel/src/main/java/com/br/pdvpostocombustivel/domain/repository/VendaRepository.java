package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findAllByDataHoraBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
}