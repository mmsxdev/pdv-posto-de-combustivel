package com.br.pdvpostocombustivel.api.venda;

import com.br.pdvpostocombustivel.api.venda.dto.VendaRequest;
import com.br.pdvpostocombustivel.api.venda.dto.VendaFinalizarRequest;
import com.br.pdvpostocombustivel.api.venda.dto.VendaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @GetMapping
    public ResponseEntity<List<VendaResponse>> getVendasPorPeriodo(
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return ResponseEntity.ok(vendaService.getVendasPorPeriodo(dataInicio, dataFim));
    }

    @PostMapping
    public ResponseEntity<VendaResponse> registrarVenda(@Valid @RequestBody VendaRequest vendaRequest) {
        return new ResponseEntity<>(vendaService.registrarVenda(vendaRequest), HttpStatus.CREATED);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<VendaResponse>> getVendasPendentes() {
        return ResponseEntity.ok(vendaService.getVendasPendentes());
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<VendaResponse> finalizarVenda(
            @PathVariable Long id,
            @Valid @RequestBody VendaFinalizarRequest request) {
        VendaResponse vendaFinalizada = vendaService.finalizarVendaPendente(id, request);
        return ResponseEntity.ok(vendaFinalizada);
    }
}