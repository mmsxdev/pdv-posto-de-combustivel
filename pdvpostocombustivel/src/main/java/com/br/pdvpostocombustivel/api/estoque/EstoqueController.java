package com.br.pdvpostocombustivel.api.estoque;

import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueRequest;
import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueResponse;
import jakarta.validation.Valid; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public ResponseEntity<List<EstoqueResponse>> getAllEstoques() {
        return ResponseEntity.ok(estoqueService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponse> getEstoqueById(@PathVariable Long id) {
        return ResponseEntity.ok(estoqueService.getById(id));
    }

    @PostMapping
    public ResponseEntity<EstoqueResponse> createEstoque(@Valid @RequestBody EstoqueRequest estoqueRequest) {
        EstoqueResponse response = estoqueService.save(estoqueRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueResponse> updateEstoque(@PathVariable Long id, @Valid @RequestBody EstoqueRequest estoqueRequest) {
        EstoqueResponse response = estoqueService.update(id, estoqueRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstoque(@PathVariable Long id) {
        estoqueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
