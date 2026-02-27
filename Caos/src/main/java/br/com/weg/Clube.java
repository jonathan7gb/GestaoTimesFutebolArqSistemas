package br.com.weg;

import java.sql.*;

// VIOLAÇÃO MONOLÍTICA: Classe faz TUDO: modelo, persistência, lógica e validação horrível
public class Clube {
    public int id;
    public String nome;
    public int anoFundacao;
    public String pais;

    // Hardcoded connection string - VIOLAÇÃO DE DIP
    private static final String URL = "jdbc:mysql://localhost:3306/gestao_futebol_caos?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "mysqlPW";

    public Clube() {}

    public Clube(int id, String nome, int anoFundacao, String pais) {
        this.id = id;
        this.nome = nome;
        this.anoFundacao = anoFundacao;
        this.pais = pais;
    }

    public Clube(String nome, int anoFundacao, String pais) {
        this.nome = nome;
        this.anoFundacao = anoFundacao;
        this.pais = pais;
    }

    // VIOLAÇÃO SRP: Salva, valida, calcula e faz tudo junto
    public void salvarNoBanco() {
        try {
            // SQL INJECTION ALERTANDO!
            Connection c = DriverManager.getConnection(URL, USER, PASS);
            String sql = "INSERT INTO clube (nome, ano_fundacao, pais) VALUES ('" + this.nome + "', " + this.anoFundacao + ", '" + this.pais + "')";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }

            System.out.println("Clube " + this.nome + " salvo com sucesso!");
            c.close();
        } catch (Exception e) {
            System.out.println("Deu erro, mas a vida segue: " + e.getMessage());
        }
    }

    // VIOLAÇÃO OCP: Se precisar de novo tipo de validação, muda a classe
    public void validarEInserirClube() throws SQLException {
        if (this.nome == null || this.nome.isEmpty()) {
            System.out.println("Nome vazio!");
            return;
        }
        if (this.pais == null || this.pais.isEmpty()) {
            System.out.println("País vazio!");
            return;
        }
        if (this.nome.length() < 3) {
            System.out.println("Nome muito curto!");
            return;
        }
        if (this.anoFundacao < 1800 || this.anoFundacao > 2100) {
            System.out.println("Ano de fundação inválido!");
            return;
        }

        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        String sql = "INSERT INTO clube (nome, ano_fundacao, pais) VALUES ('"
            + this.nome + "', " + this.anoFundacao + ", '" + this.pais + "')";

        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();

        // Buscar o ID gerado
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
        }
        conn.close();
    }

    // VIOLAÇÃO DIP: Dependência direta de banco dentro do modelo
    public void listarTodosOsClubes() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        String sql = "SELECT * FROM clube";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("\n========== TODOS OS CLUBES ==========");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") + " | Nome: " + rs.getString("nome") +
                             " | Ano Fundação: " + rs.getInt("ano_fundacao") + " | País: " + rs.getString("pais"));
        }
        conn.close();
    }

    // VIOLAÇÃO SRP: Classe faz print, banco, lógica
    public void exibirInfosCompletas() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        String sql = "SELECT * FROM clube WHERE id = " + this.id;
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("\n===== INFO DO CLUBE =====");
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Nome: " + rs.getString("nome"));
            System.out.println("Ano de Fundação: " + rs.getInt("ano_fundacao"));
            System.out.println("País: " + rs.getString("pais"));
        } else {
            System.out.println("Clube não encontrado!");
        }
        conn.close();
    }

    // VIOLAÇÃO LSP: Deletar clube sem validação
    public void deletarClubeForça() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        String sql = "DELETE FROM clube WHERE id = " + this.id;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();
        conn.close();
        System.out.println("Clube " + this.nome + " DELETADO IRREVERSIVELMENTE!");
    }

    // VIOLAÇÃO SRP + OCP: Atualizar clube misturado aqui
    public void atualizarClube() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        String sql = "UPDATE clube SET nome = '" + this.nome + "', ano_fundacao = " + this.anoFundacao + ", pais = '" + this.pais + "' WHERE id = " + this.id;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();
        conn.close();

        System.out.println("Clube " + this.nome + " atualizado com sucesso!");
    }

    // VIOLAÇÃO DIP: Retorna clube do banco aqui na classe
    public static Clube buscarClubePorid(int id) throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/gestao_futebol_caos?useSSL=false&serverTimezone=UTC";
        String USER = "root";
        String PASS = "mysqlPW";

        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        String sql = "SELECT * FROM clube WHERE id = " + id;
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Clube c = new Clube(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getInt("ano_fundacao"),
                rs.getString("pais")
            );
            conn.close();
            return c;
        }

        conn.close();
        return null;
    }
}
