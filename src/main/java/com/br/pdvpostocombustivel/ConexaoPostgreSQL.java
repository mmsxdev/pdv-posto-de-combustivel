package com.br.pdvpostocombustivel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgreSQL {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/pdv_db"; // Substitua com seus dados
        String usuario = "postgres";
        String senha = "miguel197324";

        try {
            // Registra o driver (não é mais necessário em versões mais recentes do JDBC 4.0)
            // Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão com o PostgreSQL estabelecida com sucesso!");

            // Faça suas operações com o banco de dados aqui

            conn.close(); // Fechar a conexão quando terminar
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao PostgreSQL: " + e.getMessage());
        }
    }
}