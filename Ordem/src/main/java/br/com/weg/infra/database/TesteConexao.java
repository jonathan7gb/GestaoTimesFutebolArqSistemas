package br.com.weg.infra.database;

import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {

        try{
            Conexao.conectar();
            System.out.println("Conexão feita com Sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro na conexao" + e.getMessage());
        }
    }
}
