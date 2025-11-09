package com.br.pdvpostocombustivel.api.abastecimento;

import com.br.pdvpostocombustivel.api.abastecimento.dto.AbastecimentoResponse;
import com.br.pdvpostocombustivel.api.abastecimento.dto.AtualizarValoresRequest;
import com.br.pdvpostocombustivel.api.abastecimento.dto.FinalizarAbastecimentoRequest;
import com.br.pdvpostocombustivel.api.abastecimento.dto.IniciarAbastecimentoRequest;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/abastecimentos")
@RequiredArgsConstructor
public class AbastecimentoController {

    private final AbastecimentoService abastecimentoService;

    @GetMapping
    public ResponseEntity<List<AbastecimentoResponse>> getAllAbastecimentos() {
        return ResponseEntity.ok(abastecimentoService.buscarTodos());
    }

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<AbastecimentoResponse> iniciarAbastecimento(
            @PathVariable Long id,
            @Valid @RequestBody IniciarAbastecimentoRequest request) {
        return ResponseEntity.ok(abastecimentoService.iniciarAbastecimento(id, request));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizarAbastecimento(
            @PathVariable Long id,
            @Valid @RequestBody FinalizarAbastecimentoRequest request) {
        abastecimentoService.finalizarAbastecimento(id, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/atualizar-valores")
    public ResponseEntity<AbastecimentoResponse> atualizarValores(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarValoresRequest request) {
        return ResponseEntity.ok(abastecimentoService.atualizarValores(id, request));
    }

    @PutMapping("/{id}/pausar")
    public ResponseEntity<AbastecimentoResponse> pausarAbastecimento(@PathVariable Long id) {
        AbastecimentoResponse response = abastecimentoService.pausarAbastecimento(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/retomar")
    public ResponseEntity<AbastecimentoResponse> retomarAbastecimento(@PathVariable Long id) {
        AbastecimentoResponse response = abastecimentoService.retomarAbastecimento(id);
        return ResponseEntity.ok(response);
    }
}