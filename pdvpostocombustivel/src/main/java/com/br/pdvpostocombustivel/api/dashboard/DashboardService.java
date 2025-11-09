package com.br.pdvpostocombustivel.api.dashboard;

import com.br.pdvpostocombustivel.api.dashboard.dto.DashboardMetricasResponse;
import com.br.pdvpostocombustivel.domain.entity.Venda;
import com.br.pdvpostocombustivel.domain.repository.VendaRepository;
import com.br.pdvpostocombustivel.domain.repository.EstoqueRepository;
import com.br.pdvpostocombustivel.enums.SituacaoVenda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VendaRepository vendaRepository;
    private final EstoqueRepository estoqueRepository;

    @Transactional(readOnly = true)
    public DashboardMetricasResponse getMetricasDoDia() {
        LocalDateTime inicioDoDia = LocalDate.now().atStartOfDay();
        LocalDateTime fimDoDia = LocalDate.now().atTime(LocalTime.MAX);

        List<Venda> vendasDoDia = vendaRepository.findAllByDataHoraBetweenAndSituacaoVenda(inicioDoDia, fimDoDia, SituacaoVenda.CONCLUIDA);

        BigDecimal totalVendido = vendasDoDia.stream()
                .map(Venda::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long clientesAtendidos = vendaRepository.countDistinctClientesByPeriodo(inicioDoDia, fimDoDia, SituacaoVenda.CONCLUIDA);

        return new DashboardMetricasResponse(totalVendido, clientesAtendidos);
    }

    @Transactional(readOnly = true)
    public long getContagemEstoqueCritico() {
        return estoqueRepository.countByQuantidadeLessThanEqualProdutoQuantidadeMinima();
    }
}