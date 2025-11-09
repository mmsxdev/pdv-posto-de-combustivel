package com.br.pdvpostocombustivel.api.abastecimento;

import com.br.pdvpostocombustivel.api.abastecimento.dto.AbastecimentoResponse;
import com.br.pdvpostocombustivel.api.abastecimento.dto.AtualizarValoresRequest;
import com.br.pdvpostocombustivel.api.abastecimento.dto.FinalizarAbastecimentoRequest;
import com.br.pdvpostocombustivel.api.abastecimento.dto.IniciarAbastecimentoRequest;
import com.br.pdvpostocombustivel.api.abastecimento.mapper.AbastecimentoMapper;
import com.br.pdvpostocombustivel.domain.entity.*;
import com.br.pdvpostocombustivel.domain.enums.StatusAbastecimento;
import com.br.pdvpostocombustivel.domain.repository.AbastecimentoRepository;
import com.br.pdvpostocombustivel.domain.repository.PessoaRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import com.br.pdvpostocombustivel.api.estoque.EstoqueService; // Supondo que este seja o caminho correto
import com.br.pdvpostocombustivel.domain.repository.VendaRepository;
import com.br.pdvpostocombustivel.enums.FormaPagamento;
import com.br.pdvpostocombustivel.enums.SituacaoVenda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class AbastecimentoService {

    private final AbastecimentoRepository abastecimentoRepository;
    private final ProdutoRepository produtoRepository;
    private final PessoaRepository pessoaRepository;
    private final AbastecimentoMapper abastecimentoMapper;
    private final VendaRepository vendaRepository;
    private final EstoqueService estoqueService;

    @Transactional(readOnly = true)
    public List<AbastecimentoResponse> buscarTodos() {
        // Usando um método customizado para garantir a ordem na apresentação
        return abastecimentoRepository.findAllByOrderByPistaAscNumeroBombaAsc()
                .stream()
                .map(abastecimentoMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Aqui entrarão os futuros métodos para iniciar, pausar, finalizar, etc.
    @Transactional
    public AbastecimentoResponse iniciarAbastecimento(Long idBomba, IniciarAbastecimentoRequest request) {
        Abastecimento abastecimento = abastecimentoRepository.findById(idBomba)
                .orElseThrow(() -> new RuntimeException("Bomba não encontrada com o ID: " + idBomba));

        if (abastecimento.getStatus() != StatusAbastecimento.AVAILABLE) {
            throw new IllegalStateException("Esta bomba não está disponível para iniciar um novo abastecimento.");
        }

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto (combustível) não encontrado com o ID: " + request.produtoId()));

        Pessoa frentista = pessoaRepository.findById(request.frentistaId())
                .orElseThrow(() -> new RuntimeException("Frentista não encontrado com o ID: " + request.frentistaId()));

        abastecimento.setStatus(StatusAbastecimento.ACTIVE);
        abastecimento.setPlacaVeiculo(request.placaVeiculo());
        abastecimento.setProduto(produto);
        abastecimento.setFrentista(frentista);
        abastecimento.setInicioAbastecimento(LocalDateTime.now());

        return abastecimentoMapper.toResponse(abastecimentoRepository.save(abastecimento));
    }

    @Transactional
    public void finalizarAbastecimento(Long idBomba, FinalizarAbastecimentoRequest request) {
        Abastecimento abastecimento = abastecimentoRepository.findById(idBomba)
                .orElseThrow(() -> new RuntimeException("Bomba não encontrada com o ID: " + idBomba));

        if (abastecimento.getStatus() != StatusAbastecimento.ACTIVE) {
            throw new IllegalStateException("A bomba não está em um abastecimento ativo.");
        }

        // Passo 1: Criar a Venda e o ItemVenda
        Venda novaVenda = new Venda();
        novaVenda.setDataHora(LocalDateTime.now());
        novaVenda.setValorTotal(request.valor());
        novaVenda.setSituacaoVenda(SituacaoVenda.EM_ABERTO); // A venda fica pendente de pagamento no caixa
        novaVenda.setFormaPagamento(FormaPagamento.PENDENTE); // Forma de pagamento a ser definida no caixa

        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setVenda(novaVenda);
        itemVenda.setProduto(abastecimento.getProduto());
        itemVenda.setQuantidade(request.litros());
        itemVenda.setSubtotal(request.valor());

        // Calcula o preço unitário com base nos dados finais
        BigDecimal precoUnitario = request.valor().divide(request.litros(), 3, RoundingMode.HALF_UP);
        itemVenda.setPrecoUnitario(precoUnitario);

        novaVenda.getItens().add(itemVenda);

        // Salva a Venda (e o ItemVenda em cascata)
        vendaRepository.save(novaVenda);

        // Passo 2: Dar baixa no estoque
        estoqueService.darBaixa(abastecimento.getProduto().getId(), request.litros());

        // Passo 3: Resetar a bomba para o estado DISPONÍVEL
        abastecimento.setStatus(StatusAbastecimento.AVAILABLE);
        abastecimento.setPlacaVeiculo(null);
        abastecimento.setProduto(null);
        abastecimento.setFrentista(null);
        abastecimento.setLitros(BigDecimal.ZERO);
        abastecimento.setValor(BigDecimal.ZERO);
        abastecimento.setInicioAbastecimento(null);
        abastecimento.setDuracaoMinutos(0);

        abastecimentoRepository.save(abastecimento);
    }

    @Transactional
    public AbastecimentoResponse atualizarValores(Long idBomba, AtualizarValoresRequest request) {
        Abastecimento abastecimento = abastecimentoRepository.findById(idBomba)
                .orElseThrow(() -> new RuntimeException("Bomba não encontrada com o ID: " + idBomba));

        abastecimento.setLitros(request.litros());
        abastecimento.setValor(request.valor());

        Abastecimento abastecimentoAtualizado = abastecimentoRepository.save(abastecimento);
        return abastecimentoMapper.toResponse(abastecimentoAtualizado);
    }

    @Transactional
    public AbastecimentoResponse pausarAbastecimento(Long idBomba) {
        Abastecimento abastecimento = abastecimentoRepository.findById(idBomba)
                .orElseThrow(() -> new RuntimeException("Bomba não encontrada com o ID: " + idBomba));

        if (abastecimento.getStatus() != StatusAbastecimento.ACTIVE) {
            throw new IllegalStateException("Apenas um abastecimento ativo pode ser pausado.");
        }

        abastecimento.setStatus(StatusAbastecimento.PAUSED);
        return abastecimentoMapper.toResponse(abastecimentoRepository.save(abastecimento));
    }

    @Transactional
    public AbastecimentoResponse retomarAbastecimento(Long idBomba) {
        Abastecimento abastecimento = abastecimentoRepository.findById(idBomba)
                .orElseThrow(() -> new RuntimeException("Bomba não encontrada com o ID: " + idBomba));

        if (abastecimento.getStatus() != StatusAbastecimento.PAUSED) {
            throw new IllegalStateException("Esta bomba não está pausada para ser retomada.");
        }

        abastecimento.setStatus(StatusAbastecimento.ACTIVE);
        return abastecimentoMapper.toResponse(abastecimentoRepository.save(abastecimento));
    }
}