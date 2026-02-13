package br.com.weg.infra.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static final String url = "jdbc:postgresql://dpg-d67mu4t6ubrc73967sq0-a.oregon-postgres.render.com/gestaotime";
    public static final String user = "time";
    public static final String pass = "G7wyPuKVzDb7eDUYOdoW2sXbfQNUq6jM";


    public static Connection conectar () throws SQLException{

        return DriverManager.getConnection(url, user, pass);
    }
}
