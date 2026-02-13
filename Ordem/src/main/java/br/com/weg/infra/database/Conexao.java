package br.com.weg.infra.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:postgresql://dpg-d67mu4t6ubrc73967sq0-a.oregon-postgres.render.com/gestaotime";
    private static final String USER = "time";
    private static final String PASS = "G7wyPuKVzDb7eDUYOdoW2sXbfQNUq6jM";

    public static Connection conectar ()throws SQLException{

        return DriverManager.getConnection(URL, USER, PASS);
    }
}
