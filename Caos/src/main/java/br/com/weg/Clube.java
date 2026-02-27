package br.com.weg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Clube {
    public int id;
    public String nome;
    public int anoFundacao;
    public String pais;

    // VIOLAÇÃO DE SRP: O modelo conhece o banco de dados
    public void salvarNoBanco() {
        try {
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestao_futebol_caos?useSSL=false&serverTimezone=UTC", "root", "kauafelix123@");
            String sql = "INSERT INTO clube (nome) VALUES ('" + this.nome + "')";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Clube " + this.nome + " salvo com sucesso (eu acho)!");
        } catch (Exception e) {
            System.out.println("Deu erro, mas a vida segue.");
        }
    }
}
