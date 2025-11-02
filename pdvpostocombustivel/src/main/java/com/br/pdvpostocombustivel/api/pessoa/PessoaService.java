package com.br.pdvpostocombustivel.api.pessoa;


import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivel.api.contato.dto.ContatoResponse;
import com.br.pdvpostocombustivel.api.pessoa.dto.PessoaRequest;
import com.br.pdvpostocombustivel.api.pessoa.dto.PessoaResponse;
import com.br.pdvpostocombustivel.domain.entity.Acesso;
import com.br.pdvpostocombustivel.domain.entity.Contato;
import com.br.pdvpostocombustivel.domain.entity.Pessoa;
import com.br.pdvpostocombustivel.domain.repository.PessoaRepository;
import com.br.pdvpostocombustivel.exception.PessoaException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PessoaService {

    // implementa a interface repository de pessoa
    private final PessoaRepository repository;
    private final PasswordEncoder passwordEncoder;


    public PessoaService(PessoaRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE
    public PessoaResponse create(PessoaRequest req) {
        Pessoa pessoa = toEntity(new Pessoa(), req);
        return toResponse(repository.save(pessoa));
    }

    // READ by ID - validar a utilização desse método
    @Transactional(readOnly = true)
    public PessoaResponse getById(Long id) {
        Pessoa p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada. id=" + id));
        return toResponse(p);
    }

    // READ by CPF/CNPJ
    @Transactional(readOnly = true)
    public PessoaResponse getByCpfCnpj(String cpfCnpj) {
        Pessoa p = repository.findByCpfCnpj(cpfCnpj)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada. cpfCnpj=" + cpfCnpj));
        return toResponse(p);
    }

    // LIST paginado
    @Transactional(readOnly = true)
    public Page<PessoaResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return repository.findAll(pageable).map(this::toResponse);
    }

    // UPDATE  - substitui todos os campos
    public PessoaResponse update(Long id, PessoaRequest req) {
        Pessoa p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada. id=" + id));

        if (req.cpfCnpj() != null && !req.cpfCnpj().equals(p.getCpfCnpj())) {
            validarUnicidadeCpfCnpj(req.cpfCnpj(), id);
        }
        return toResponse(repository.save(toEntity(p, req)));
    }

    // PATCH - atualiza apenas campos não nulos
    public PessoaResponse patch(Long id, PessoaRequest req) {
        Pessoa p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada. id=" + id));

        // O método toEntity já lida com campos nulos para patch
        return toResponse(repository.save(p));
    }

    // DELETE
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Pessoa não encontrada. id=" + id);
        }
        repository.deleteById(id);
    }

    // ---------- Helpers ----------
    private void validarUnicidadeCpfCnpj(String cpfCnpj, Long idAtual) {
        repository.findByCpfCnpj(cpfCnpj).ifPresent(existente -> {
            if (idAtual == null || !existente.getId().equals(idAtual)) {
                throw new DataIntegrityViolationException("CPF/CNPJ já cadastrado: " + cpfCnpj);
            }
        });
    }

    private Pessoa toEntity(Pessoa pessoa, PessoaRequest req) {
        pessoa.setNomeCompleto(req.nomeCompleto());
        pessoa.setCpfCnpj(req.cpfCnpj());
        pessoa.setNumeroCtps(req.numeroCtps());
        pessoa.setDataNascimento(req.dataNascimento());
        pessoa.setTipoPessoa(req.tipoPessoa());

        // Mapeia Contato
        if (req.contato() != null) {
            Contato contato = pessoa.getContato() == null ? new Contato() : pessoa.getContato();
            contato.setTelefone(req.contato().telefone());
            contato.setEmail(req.contato().email());
            contato.setEndereco(req.contato().endereco());
            pessoa.setContato(contato);
        }

        // Mapeia Acesso
        if (req.acesso() != null) {
            // Validação para garantir que o usuário não seja duplicado
            repository.findByAcessoUsuario(req.acesso().usuario()).ifPresent(p -> {
                if (!p.getId().equals(pessoa.getId())) {
                    throw new PessoaException("Nome de usuário '" + req.acesso().usuario() + "' já está em uso.");
                }
            });

            Acesso acesso = pessoa.getAcesso() == null ? new Acesso() : pessoa.getAcesso();
            acesso.setUsuario(req.acesso().usuario());
            acesso.setTipoAcesso(req.acesso().tipoAcesso());
            // Apenas atualiza a senha se uma nova for fornecida
            if (req.acesso().senha() != null && !req.acesso().senha().isBlank()) {
                acesso.setSenha(passwordEncoder.encode(req.acesso().senha()));
            } else if (acesso.getId() == null) { // Se for um novo acesso, a senha é obrigatória
                throw new PessoaException("A senha é obrigatória para criar um novo acesso.");
            }
            pessoa.setAcesso(acesso);
        } else {
            // Se o request não envia acesso, remove o acesso existente (se houver)
            pessoa.setAcesso(null);
        }

        return pessoa;
    }

    private PessoaResponse toResponse(Pessoa p) {
        ContatoResponse contatoResponse = (p.getContato() != null)
                ? new ContatoResponse(p.getContato().getId(), p.getContato().getTelefone(), p.getContato().getEmail(), p.getContato().getEndereco())
                : null;

        AcessoResponse acessoResponse = (p.getAcesso() != null)
                ? new AcessoResponse(p.getAcesso().getId(), p.getAcesso().getUsuario(), p.getAcesso().getTipoAcesso())
                : null;

        return new PessoaResponse(
                p.getId(),
                p.getNomeCompleto(),
                p.getCpfCnpj(),
                p.getNumeroCtps(),
                p.getDataNascimento(),
                p.getTipoPessoa(),
                contatoResponse,
                acessoResponse
        );
    }
}