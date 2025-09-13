package com.br.pdvpostocombustivel;

import com.br.pdvpostocombustivel.domain.entity.Pessoa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdvpostocombustivelApplication {

	public static void main(String[] args) {
		//SpringApplication.run(PdvpostocombustivelApplication.class, args);

        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNomeCompleto("Mateus");
        pessoa1.setCpfCnpj("49098328911");
        pessoa1.setNumeroCtps(43224);

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNomeCompleto("Jonas");
        pessoa2.setCpfCnpj("96598328911");
        pessoa2.setNumeroCtps(63894);

        Pessoa pessoa3 = new Pessoa();
        pessoa3.setNomeCompleto("Murilo");
        pessoa3.setCpfCnpj("68494654441");
        pessoa3.setNumeroCtps(12345);

        System.out.println("Nome Completo: " + pessoa1.getNomeCompleto());
        System.out.println("CPF/CNPJ: " + pessoa1.getCpfCnpj());
        System.out.println("N° CTPS: " + pessoa1.getNumeroCtps());
        System.out.println("-------------------------------------------------------");

        System.out.println("Nome Completo: " + pessoa2.getNomeCompleto());
        System.out.println("CPF/CNPJ: " + pessoa2.getCpfCnpj());
        System.out.println("N° CTPS: " + pessoa2.getNumeroCtps());
        System.out.println("-------------------------------------------------------");

        System.out.println("Nome Completo: " + pessoa3.getNomeCompleto());
        System.out.println("CPF/CNPJ: " + pessoa3.getCpfCnpj());
        System.out.println("N° CTPS: " + pessoa3.getNumeroCtps());
        System.out.println("-------------------------------------------------------");
	}
}
