package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostocombustivel.api.custo.dto.CustoResponse;
import com.br.pdvpostocombustivel.domain.entity.Custo;
import com.br.pdvpostocombustivel.domain.repository.CustoRepository;
import com.br.pdvpostocombustivel.exception.CustoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustoService {

    private final CustoRepository custoRepository;

    public CustoService(CustoRepository custoRepository) {
        this.custoRepository = custoRepository;
    }

    @Transactional(readOnly = true)
    public List<CustoResponse> getAll() {
        return custoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustoResponse getById(Long id) {
        return custoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new CustoException("Custo com ID " + id + " não encontrado."));
    }

    @Transactional
    public CustoResponse save(CustoRequest custoRequest) {
        Custo custo = new Custo();
        fromRequest(custo, custoRequest);
        return toResponse(custoRepository.save(custo));
    }

    @Transactional
    public CustoResponse update(Long id, CustoRequest custoRequest) {
        Custo custo = custoRepository.findById(id)
                .orElseThrow(() -> new CustoException("Custo com ID " + id + " não encontrado para atualização."));
        fromRequest(custo, custoRequest);
        return toResponse(custoRepository.save(custo));
    }

    @Transactional
    public void delete(Long id) {
        if (!custoRepository.existsById(id)) {
            throw new CustoException("Custo com ID " + id + " não encontrado para exclusão.");
        }
        custoRepository.deleteById(id);
    }

    private CustoResponse toResponse(Custo custo) {
        return new CustoResponse(custo.getId(), custo.getTipoCusto(), custo.getImposto(), custo.getCustoVariavel(),
                custo.getCustoFixo(), custo.getMargemLucro(), custo.getDataProcessamento());
    }

    private void fromRequest(Custo custo, CustoRequest request) {
        custo.setTipoCusto(request.tipoCusto());
        custo.setImposto(request.imposto());
        custo.setCustoVariavel(request.custoVariavel());
        custo.setCustoFixo(request.custoFixo());
        custo.setMargemLucro(request.margemLucro());
        custo.setDataProcessamento(request.dataProcessamento());
    }
}