package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acessos")
public class AcessoController {

    private final AcessoService acessoService;

    public AcessoController(AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    @GetMapping
    public ResponseEntity<List<AcessoResponse>> getAllAcessos() {
        return ResponseEntity.ok(acessoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcessoResponse> getAcessoById(@PathVariable Long id) {
        return ResponseEntity.ok(acessoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AcessoResponse> createAcesso(@Valid @RequestBody AcessoRequest acessoRequest) {
        return new ResponseEntity<>(acessoService.save(acessoRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcessoResponse> updateAcesso(@PathVariable Long id, @Valid @RequestBody AcessoRequest acessoRequest) {
        return ResponseEntity.ok(acessoService.update(id, acessoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcesso(@PathVariable Long id) {
        acessoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}