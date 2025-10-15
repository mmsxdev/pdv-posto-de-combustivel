package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/precos")
@RequiredArgsConstructor
public class PrecoController {

    private final PrecoService precoService;

    @GetMapping
    public ResponseEntity<List<PrecoResponse>> getAllPrecos() {
        return ResponseEntity.ok(precoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrecoResponse> getPrecoById(@PathVariable Long id) {
        return ResponseEntity.ok(precoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PrecoResponse> createPreco(@Valid @RequestBody PrecoRequest precoRequest) {
        return new ResponseEntity<>(precoService.save(precoRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrecoResponse> updatePreco(@PathVariable Long id, @Valid @RequestBody PrecoRequest precoRequest) {
        return ResponseEntity.ok(precoService.update(id, precoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreco(@PathVariable Long id) {
        precoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}