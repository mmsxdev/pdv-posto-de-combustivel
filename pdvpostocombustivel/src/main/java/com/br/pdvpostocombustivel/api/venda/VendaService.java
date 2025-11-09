package com.br.pdvpostocombustivel.api.venda;

import com.br.pdvpostocombustivel.api.venda.dto.ItemVendaResponse;
import com.br.pdvpostocombustivel.api.venda.dto.VendaFinalizarRequest;
import com.br.pdvpostocombustivel.api.venda.dto.VendaResponse;
import com.br.pdvpostocombustivel.api.venda.dto.VendaRequest;
import com.br.pdvpostocombustivel.domain.entity.*;
import com.br.pdvpostocombustivel.domain.repository.*;
import com.br.pdvpostocombustivel.domain.repository.EstoqueRepository;
import com.br.pdvpostocombustivel.enums.SituacaoVenda;
import com.br.pdvpostocombustivel.exception.VendaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final PessoaRepository pessoaRepository;
    private final PrecoRepository precoRepository;
    private final EstoqueRepository estoqueRepository;

    @Transactional(readOnly = true)
    public List<VendaResponse> getVendasPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        // Garante que a data final inclua todo o dia.
        LocalDateTime dataFimAjustada = dataFim.withHour(23).withMinute(59).withSecond(59);

        return vendaRepository.findAllByDataHoraBetween(dataInicio, dataFimAjustada).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VendaResponse> getVendasPendentes() {
        return vendaRepository.findAllBySituacaoVenda(SituacaoVenda.EM_ABERTO)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public VendaResponse finalizarVendaPendente(Long vendaId, VendaFinalizarRequest request) {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new VendaException("Venda com ID " + vendaId + " não encontrada."));

        if (venda.getSituacaoVenda() != SituacaoVenda.EM_ABERTO) {
            throw new VendaException("Esta venda não está pendente e não pode ser finalizada.");
        }

        venda.setFormaPagamento(request.formaPagamento());
        venda.setSituacaoVenda(SituacaoVenda.CONCLUIDA);
        return toResponse(vendaRepository.save(venda));
    }

    @Transactional
    public VendaResponse registrarVenda(VendaRequest vendaRequest) {
        Venda venda = new Venda();
        venda.setDataHora(LocalDateTime.now());
        venda.setFormaPagamento(vendaRequest.formaPagamento());
        venda.setSituacaoVenda(SituacaoVenda.CONCLUIDA);

        // Associa o cliente, se informado
        if (vendaRequest.clienteId() != null) {
            Pessoa cliente = pessoaRepository.findById(vendaRequest.clienteId())
                    .orElseThrow(() -> new VendaException("Cliente com ID " + vendaRequest.clienteId() + " não encontrado."));
            venda.setCliente(cliente);
        }

        BigDecimal valorTotalVenda = BigDecimal.ZERO;
        List<ItemVenda> itensVenda = new ArrayList<>();

        // Processa cada item da venda
        for (var itemReq : vendaRequest.itens()) {
            Produto produto = produtoRepository.findById(itemReq.produtoId())
                    .orElseThrow(() -> new VendaException("Produto com ID " + itemReq.produtoId() + " não encontrado."));

            // Encontra o preço atual do produto
            Preco preco = precoRepository.findFirstByProdutoAndDataVigenciaLessThanEqualOrderByDataVigenciaDesc(produto, LocalDate.now())
                    .orElseThrow(() -> new VendaException("Produto '" + produto.getNome() + "' não possui preço cadastrado."));

            BigDecimal subtotal = preco.getValor().multiply(itemReq.quantidade());

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setVenda(venda);
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(itemReq.quantidade());
            itemVenda.setPrecoUnitario(preco.getValor());
            itemVenda.setSubtotal(subtotal);

            itensVenda.add(itemVenda);
            valorTotalVenda = valorTotalVenda.add(subtotal);

            // Lógica de baixa de estoque (FIFO)
            Estoque estoqueParaBaixa = estoqueRepository.findFirstByProdutoAndQuantidadeGreaterThanEqualOrderByDataValidadeAsc(produto, itemReq.quantidade())
                    .orElseThrow(() -> new VendaException("Estoque insuficiente para o produto: " + produto.getNome()));

            BigDecimal novaQuantidade = estoqueParaBaixa.getQuantidade().subtract(itemReq.quantidade());
            estoqueParaBaixa.setQuantidade(novaQuantidade);
            // O save não é necessário aqui, pois a entidade está sendo gerenciada pelo Hibernate.
            // A alteração será persistida no final da transação.

        }

        venda.setItens(itensVenda);
        venda.setValorTotal(valorTotalVenda);

        Venda vendaSalva = vendaRepository.save(venda);
        return toResponse(vendaSalva);
    }

    private VendaResponse toResponse(Venda venda) {
        List<ItemVendaResponse> itensResponse = venda.getItens().stream()
                .map(item -> new ItemVendaResponse(
                        item.getId(),
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getProduto().getReferencia(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        Long clienteId = venda.getCliente() != null ? venda.getCliente().getId() : null;
        String nomeCliente = venda.getCliente() != null ? venda.getCliente().getNomeCompleto() : "Consumidor não identificado";

        return new VendaResponse(
                venda.getId(),
                venda.getNumeroNota(),
                venda.getDataHora(),
                venda.getValorTotal(),
                venda.getFormaPagamento(),
                venda.getSituacaoVenda(),
                clienteId,
                nomeCliente,
                itensResponse
        );
    }
}