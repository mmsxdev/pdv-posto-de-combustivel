package com.br.pdvpostocombustivel.api.produto;

import com.br.pdvpostocombustivel.api.produto.dto.ProdutoRequest;
import com.br.pdvpostocombustivel.api.produto.dto.ProdutoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getAllProdutos() {
        return ResponseEntity.ok(produtoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getProdutoById(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> createProduto(@Valid @RequestBody ProdutoRequest produtoRequest) {
        return new ResponseEntity<>(produtoService.save(produtoRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> updateProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequest produtoRequest) {
        return ResponseEntity.ok(produtoService.update(id, produtoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}