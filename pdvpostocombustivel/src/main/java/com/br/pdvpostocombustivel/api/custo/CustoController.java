package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostocombustivel.api.custo.dto.CustoResponse;
import jakarta.validation.Valid; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/custos")
public class CustoController {

    private final CustoService custoService;

    public CustoController(CustoService custoService) {
        this.custoService = custoService;
    }

    @GetMapping
    public ResponseEntity<List<CustoResponse>> getAllCustos() {
        return ResponseEntity.ok(custoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustoResponse> getCustoById(@PathVariable Long id) {
        return ResponseEntity.ok(custoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CustoResponse> createCusto(@Valid @RequestBody CustoRequest custoRequest) {
        CustoResponse response = custoService.save(custoRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustoResponse> updateCusto(@PathVariable Long id, @Valid @RequestBody CustoRequest custoRequest) {
        return ResponseEntity.ok(custoService.update(id, custoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCusto(@PathVariable Long id) {
        custoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}