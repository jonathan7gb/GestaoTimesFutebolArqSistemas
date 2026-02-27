package br.com.weg;

import java.sql.*;

// VIOLAÇÃO MONOLÍTICA EXTREMA: Usuário faz TUDO sozinho
public class Usuario {
    public int id;
    public String nome;
    public double peso;
    public int altura;
    public String tipo; // "JOGADOR", "TECNICO", "PRESIDENTE", "QUALQUER_COISA"
    public int idClube;
    public double multa;

    // HARDCODED CONNECTION - VIOLAÇÃO DIP
    private static final String URL = "jdbc:mysql://localhost:3306/gestao_futebol_caos?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "mysqlPW";

    public Usuario() {}

    public Usuario(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public Usuario(int id, String nome, double peso, int altura, String tipo, int idClube, double multa) {
        this.id = id;
        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.tipo = tipo;
        this.idClube = idClube;
        this.multa = multa;
    }

    // VIOLAÇÃO SRP GIGANTESCA: Valida, insere, calcula multa, atualiza tudo junto
    public void criarUsuarioCompleto() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        // Validação misturada com lógica de negócio
        if (this.nome == null || this.nome.isEmpty()) {
            System.out.println("Nome inválido!");
            return;
        }

        // Se é jogador e está acima do peso, aplica multa automaticamente (horrível!)
        if (this.tipo.equals("JOGADOR")) {
            if (this.peso > 120) {
                this.multa = 500.0;
                System.out.println("ALERTA: Jogador " + this.nome + " fora do peso! Multa de R$ " + this.multa);
            }

            // SQL INJECTION aqui também
            String sql = "INSERT INTO usuario (nome, tipo, peso, altura, id_clube, multa) VALUES ('"
                + this.nome + "', '" + this.tipo + "', " + this.peso + ", " + this.altura + ", " + this.idClube + ", " + this.multa + ")";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
        else if (this.tipo.equals("TECNICO")) {
            String sql = "INSERT INTO usuario (nome, tipo, peso, altura, id_clube) VALUES ('"
                + this.nome + "', '" + this.tipo + "', " + this.peso + ", " + this.altura + ", " + this.idClube + ")";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
        else if (this.tipo.equals("PRESIDENTE")) {
            // Presidente tem privilégios especiais (sem validação!)
            String sql = "INSERT INTO usuario (nome, tipo, id_clube) VALUES ('"
                + this.nome + "', '" + this.tipo + "', " + this.idClube + ")";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
        else {
            // Qualquer coisa mesmo!
            String sql = "INSERT INTO usuario (nome, tipo, peso, altura, id_clube, multa) VALUES ('"
                + this.nome + "', '" + this.tipo + "', " + this.peso + ", " + this.altura + ", " + this.idClube + ", " + this.multa + ")";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }

        conn.close();
    }

    // VIOLAÇÃO SRP: Lista, filtra, calcula e exibe tudo junto
    public void listarTodosECalcularEstatisticas() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        String sql = "SELECT * FROM usuario WHERE tipo = '" + this.tipo + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        double totalPeso = 0;
        int totalUsuarios = 0;

        System.out.println("\n========== LISTANDO " + this.tipo + "S ==========");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") + " | Nome: " + rs.getString("nome") +
                             " | Peso: " + rs.getDouble("peso") + "kg | Multa: R$ " + rs.getDouble("multa"));
            totalPeso += rs.getDouble("peso");
            totalUsuarios++;
        }

        // Calcula média
        double mediaPeso = totalUsuarios > 0 ? totalPeso / totalUsuarios : 0;
        System.out.println("Total de " + this.tipo + "s: " + totalUsuarios);
        System.out.println("Peso total: " + totalPeso + "kg");
        System.out.println("Peso médio: " + mediaPeso + "kg");

        conn.close();
    }

    // VIOLAÇÃO SRP + OCP: Deleta e faz tudo ao mesmo tempo
    public void deletarUsuario() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        // Deleta sem validação
        String sqlDelete = "DELETE FROM usuario WHERE id = " + this.id;
        PreparedStatement ps = conn.prepareStatement(sqlDelete);
        ps.executeUpdate();

        System.out.println("Usuário " + this.nome + " deletado IRREVERSIVELMENTE!");
        conn.close();
    }

    // VIOLAÇÃO SRP: Aplica multa aqui mesmo
    public void aplicarMulta(double valorMulta) throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        // Validação dentro de método de aplicar multa
        if (valorMulta <= 0) {
            System.out.println("Multa inválida!");
            return;
        }

        // Atualiza usuário
        String sql = "UPDATE usuario SET multa = multa + " + valorMulta + " WHERE id = " + this.id;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();

        conn.close();

        System.out.println("Multa de R$ " + valorMulta + " aplicada ao usuário " + this.nome);
    }

    // VIOLAÇÃO SRP: Atualiza usuário misturado aqui
    public void atualizarUsuario() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        String sql = "UPDATE usuario SET nome = '" + this.nome + "', peso = " + this.peso + ", altura = " + this.altura + ", tipo = '" + this.tipo + "' WHERE id = " + this.id;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();

        conn.close();
        System.out.println("Usuário " + this.nome + " atualizado!");
    }

    // VIOLAÇÃO DIP: Retorna usuário do banco aqui na classe
    public static Usuario buscarUsuarioPorId(int id) throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/gestao_futebol_caos?useSSL=false&serverTimezone=UTC";
        String USER = "root";
        String PASS = "mysqlPW";

        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        String sql = "SELECT * FROM usuario WHERE id = " + id;
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Usuario u = new Usuario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getDouble("peso"),
                rs.getInt("altura"),
                rs.getString("tipo"),
                rs.getInt("id_clube"),
                rs.getDouble("multa")
            );
            conn.close();
            return u;
        }

        conn.close();
        return null;
    }

    // VIOLAÇÃO OCP: Para cada novo tipo, esse método cresce infinitamente
    public void executarAcaoDoTipo() {
        if (tipo.equals("JOGADOR")) {
            System.out.println(nome + " está correndo atrás da bola...");
        } else if (tipo.equals("TECNICO")) {
            System.out.println(nome + " está gritando na beira do campo...");
        } else if (tipo.equals("PRESIDENTE")) {
            System.out.println(nome + " está assinando cheques...");
        } else if (tipo.equals("MASSAGISTA")) {
            System.out.println(nome + " está massageando os jogadores...");
        } else {
            System.out.println(nome + " está fazendo... algo no clube?");
        }
    }

    // VIOLAÇÃO SRP: Model formatando texto para a UI
    public String gerarLinhaRelatorio() {
        return "ID: " + id + " | NOME: " + nome.toUpperCase() + " | TIPO: " + tipo + " | PESO: " + peso + "kg | MULTA: R$ " + multa;
    }
}