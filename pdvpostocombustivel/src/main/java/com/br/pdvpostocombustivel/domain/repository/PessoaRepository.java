package com.br.pdvpostocombustivel.domain.repository;


import com.br.pdvpostocombustivel.domain.entity.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByNomeCompleto(String nomeCompleto);

    Optional<Pessoa> findByCpfCnpj(String cpfCnpj);

    boolean existsByCpfCnpj(String cpfCnpj);

    boolean existsByNomeCompleto(String nomeCompleto);

    Optional<Pessoa> findByAcessoUsuario(String usuario);

    /**
     * Busca uma página de pessoas, trazendo os relacionamentos 'acesso' e 'contato'
     * na mesma consulta para evitar o problema N+1.
     */
    @Query(value = "SELECT p FROM Pessoa p LEFT JOIN FETCH p.acesso LEFT JOIN FETCH p.contato",
           countQuery = "SELECT count(p) FROM Pessoa p")
    Page<Pessoa> findAllWithDetails(Pageable pageable);
}
