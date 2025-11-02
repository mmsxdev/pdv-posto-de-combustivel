package com.br.pdvpostocombustivel.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Ferramenta simples para gerar senhas criptografadas com BCrypt.
 * Execute o método main() para gerar um novo hash.
 */
public class PasswordGeneratorUtil {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaPlana = "admin123"; // A senha que você quer criptografar
        String senhaCriptografada = passwordEncoder.encode(senhaPlana);

        System.out.println("====================================================================");
        System.out.println("Senha Plana: " + senhaPlana);
        System.out.println("Senha Criptografada (BCrypt): " + senhaCriptografada);
        System.out.println("====================================================================");
        System.out.println("Copie a senha criptografada acima e cole no seu arquivo data.sql");
    }
}