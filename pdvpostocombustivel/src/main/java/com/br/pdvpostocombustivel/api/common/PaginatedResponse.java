package com.br.pdvpostocombustivel.api.common;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * DTO padrão para respostas paginadas, fornecendo uma estrutura JSON estável.
 * @param <T> O tipo do conteúdo da página.
 */
@Getter
public class PaginatedResponse<T> {

    private final List<T> content;
    private final int currentPage;
    private final long totalItems;
    private final int totalPages;
    private final boolean last;

    public PaginatedResponse(Page<T> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber();
        this.totalItems = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }
}