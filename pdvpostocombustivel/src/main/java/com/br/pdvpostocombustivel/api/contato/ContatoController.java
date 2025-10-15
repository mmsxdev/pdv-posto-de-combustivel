package com.br.pdvpostocombustivel.api.contato;

import com.br.pdvpostocombustivel.api.contato.dto.ContatoRequest;
import com.br.pdvpostocombustivel.api.contato.dto.ContatoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contatos")
public class ContatoController {

    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @GetMapping
    public ResponseEntity<List<ContatoResponse>> getAllContatos() {
        return ResponseEntity.ok(contatoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoResponse> getContatoById(@PathVariable Long id) {
        return ResponseEntity.ok(contatoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ContatoResponse> createContato(@Valid @RequestBody ContatoRequest contatoRequest) {
        return new ResponseEntity<>(contatoService.save(contatoRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoResponse> updateContato(@PathVariable Long id, @Valid @RequestBody ContatoRequest contatoRequest) {
        return ResponseEntity.ok(contatoService.update(id, contatoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContato(@PathVariable Long id) {
        contatoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}