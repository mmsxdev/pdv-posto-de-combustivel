package com.br.pdvpostocombustivel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    // Rotas públicas
                    auth.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
                    auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();

                    // Rotas de CADASTRO (Gestão)
                    auth.requestMatchers("/api/v1/pessoas/**", "/api/produtos/**", "/api/precos/**", "/api/estoque/**", "/api/custos/**")
                            .hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_GERENCIA");

                    // Rota de REGISTRO DE VENDA (Operação)
                    auth.requestMatchers(HttpMethod.POST, "/api/vendas")
                            .hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_FUNCIONARIO");

                    // Rota de RELATÓRIO (Gestão)
                    auth.requestMatchers(HttpMethod.GET, "/api/vendas")
                            .hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_GERENCIA");

                    // Qualquer outra requisição precisa estar autenticada
                    auth.anyRequest().authenticated();
                })
                // Adiciona nosso filtro para ser executado antes do filtro padrão de autenticação
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
