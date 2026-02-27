package br.com.weg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

// VIOLAÇÃO EXTREMA DE TUDO: Toda a aplicação em UM ÚNICO MÉTODO
public class Main {
    // HARDCODED - VIOLAÇÃO DE DIP
    static final String URL = "jdbc:mysql://localhost:3306/gestao_futebol_caos?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "mysqlPW";

    public static void main(String[] args) throws Exception {
        // VIOLAÇÃO DE DIP: Dependência direta de Scanner aqui na Main
        Scanner s = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("!!! BEM-VINDO AO SISTEMA DE GESTÃO DO CAOS FC !!!");
        System.out.println("Prepare-se para o pior código que você já viu...\n");

        while (continuar) {
            System.out.println("\n========== MENU PRINCIPAL ==========");
            System.out.println("1 - Cadastrar Clube");
            System.out.println("2 - Cadastrar Usuário");
            System.out.println("3 - Aplicar Multa");
            System.out.println("4 - Simular Jogo");
            System.out.println("5 - Listar Clubes");
            System.out.println("6 - Listar Usuários");
            System.out.println("7 - Deletar Clube");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            String opcao = s.next();
            s.nextLine(); // Limpa o buffer

            // VIOLAÇÃO SRP GIGANTESCA: Toda lógica misturada
            switch (opcao) {
                case "1":
                    // VIOLAÇÃO SRP: Aqui faz input, validação, banco e tudo junto
                    System.out.print("Nome do Time: ");
                    String nomeClube = s.nextLine();
                    System.out.print("Ano de Fundação: ");
                    int anoFundacao = 0;
                    try {
                        anoFundacao = s.nextInt();
                        s.nextLine(); // Limpa o buffer
                    } catch (IllegalArgumentException e) {
                        System.out.println("✗ Ano de fundação inválido!");
                        s.nextLine(); // Limpa o buffer
                        break;
                    }
                    System.out.print("País: ");
                    String paisClube = s.nextLine();

                    // VALIDAÇÃO MÍNIMA
                    if (nomeClube.isEmpty() || paisClube.isEmpty()) {
                        System.out.println("Dados incompletos!");
                        break;
                    }

                    try {
                        Connection conn = DriverManager.getConnection(URL, USER, PASS);

                        // SQL INJECTION PURO! Concatenando String
                        String sql = "INSERT INTO clube (nome, ano_fundacao, pais) VALUES ('"
                            + nomeClube + "', " + anoFundacao + ", '" + paisClube + "')";

                        System.out.println("SQL gerado: " + sql); // DEBUG desnecessário

                        PreparedStatement ps = conn.prepareStatement(sql);
                        int resultado = ps.executeUpdate();

                        if (resultado > 0) {
                            System.out.println("✓ Clube " + nomeClube + " cadastrado com sucesso!");
                        }

                        conn.close();
                    } catch (Exception e) {
                        System.out.println("✗ ERRO ao cadastrar clube: " + e.getMessage());
                        e.printStackTrace(); // Exposição de stack trace
                    }
                    break;

                case "2":
                    // VIOLAÇÃO SRP: Mistura input, validação, lógica de negócio, banco
                    System.out.print("Nome: ");
                    String nomeUsuario = s.nextLine();
                    System.out.print("Peso (kg): ");
                    double peso = 0;
                    try {
                        peso = s.nextDouble();
                        s.nextLine(); // Limpa o buffer
                    } catch (IllegalArgumentException e) {
                        System.out.println("✗ Peso inválido!");
                        s.nextLine();
                        break;
                    }
                    System.out.print("Altura (cm): ");
                    int altura = 0;
                    try {
                        altura = s.nextInt();
                        s.nextLine(); // Limpa o buffer
                    } catch (IllegalArgumentException e) {
                        System.out.println("✗ Altura inválida!");
                        s.nextLine();
                        break;
                    }
                    System.out.print("Tipo (JOGADOR/TECNICO/PRESIDENTE): ");
                    String tipo = s.nextLine();
                    System.out.print("ID do Clube: ");
                    int idClube = 0;
                    try {
                        idClube = s.nextInt();
                        s.nextLine(); // Limpa o buffer
                    } catch (IllegalArgumentException e) {
                        System.out.println("✗ ID do clube inválido!");
                        s.nextLine();
                        break;
                    }

                    Usuario u = new Usuario();
                    u.nome = nomeUsuario;
                    u.peso = peso;
                    u.altura = altura;
                    u.tipo = tipo;
                    u.idClube = idClube;

                    // VIOLAÇÃO OCP + SRP: Lógica de negócio aqui
                    double multa = 0;
                    if (tipo.equals("JOGADOR") && peso > 120) {
                        multa = 500; // Multa hardcoded
                        System.out.println("⚠ ALERTA: Jogador fora de forma! Multa de R$ " + multa);
                    }

                    try {
                        Connection conn = DriverManager.getConnection(URL, USER, PASS);

                        // SQL INJECTION
                        String sql = "INSERT INTO usuario (nome, tipo, peso, altura, id_clube, multa) VALUES ('"
                            + nomeUsuario + "', '" + tipo + "', " + peso + ", " + altura + ", " + idClube + ", " + multa + ")";

                        PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
                        ps.executeUpdate();

                        ResultSet rs = ps.getGeneratedKeys();
                        if (rs.next()) {
                            u.id = rs.getInt(1);
                            System.out.println("✓ Usuário cadastrado com ID: " + u.id);
                        }

                        conn.close();
                    } catch (Exception e) {
                        System.out.println("✗ ERRO ao cadastrar usuário: " + e.getMessage());
                    }
                    break;

                case "3":
                    // VIOLAÇÃO SRP: Aplica multa toda aqui
                    System.out.print("ID do usuário para multar: ");
                    int idUserMulta = 0;
                    try {
                        idUserMulta = s.nextInt();
                        s.nextLine(); // Limpa o buffer
                    } catch (IllegalArgumentException e) {
                        System.out.println("✗ ID do usuário inválido!");
                        s.nextLine();
                        break;
                    }
                    System.out.print("Valor da multa (R$): ");
                    double valorMulta = 0;
                    try {
                        valorMulta = s.nextDouble();
                        s.nextLine(); // Limpa o buffer
                    } catch (IllegalArgumentException e) {
                        System.out.println("✗ Valor da multa inválido!");
                        s.nextLine();
                        break;
                    }

                    try {
                        Connection conn = DriverManager.getConnection(URL, USER, PASS);

                        // Atualiza usuário
                        String sql = "UPDATE usuario SET multa = multa + " + valorMulta + " WHERE id = " + idUserMulta;
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.executeUpdate();

                        System.out.println("✓ Multa de R$ " + valorMulta + " aplicada!");

                        conn.close();
                    } catch (Exception e) {
                        System.out.println("✗ ERRO: " + e.getMessage());
                    }
                    break;

                case "4":
                    // VIOLAÇÃO SRP: Simula jogo aqui tudo misturado
                    System.out.println("\n===== SIMULADOR DE JOGO =====");
                    System.out.print("ID do Clube A: ");
                    int clubeA = 0;
                    try {
                        clubeA = s.nextInt();
                        s.nextLine(); // Limpa o buffer
                    } catch (IllegalArgumentException e) {
                        System.out.println("✗ ID do Clube A inválido!");
                        s.nextLine();
                        break;
                    }
                    System.out.print("ID do Clube B: ");
                    int clubeB = 0;
                    try {
                        clubeB = s.nextInt();
                        s.nextLine(); // Limpa o buffer
                    } catch (IllegalArgumentException e) {
                        System.out.println("✗ ID do Clube B inválido!");
                        s.nextLine();
                        break;
                    }

                    try {
                        Connection conn = DriverManager.getConnection(URL, USER, PASS);

                        // Busca nomes dos clubes
                        String sql1 = "SELECT nome FROM clube WHERE id = " + clubeA;
                        String sql2 = "SELECT nome FROM clube WHERE id = " + clubeB;

                        PreparedStatement ps1 = conn.prepareStatement(sql1);
                        PreparedStatement ps2 = conn.prepareStatement(sql2);

                        ResultSet rs1 = ps1.executeQuery();
                        ResultSet rs2 = ps2.executeQuery();

                        String nomeA = "", nomeB = "";
                        if (rs1.next()) nomeA = rs1.getString("nome");
                        if (rs2.next()) nomeB = rs2.getString("nome");

                        // Simula resultado aleatório
                        int golsA = (int) (Math.random() * 5);
                        int golsB = (int) (Math.random() * 5);

                        System.out.println("\n JOGO: " + nomeA + " vs " + nomeB);
                        System.out.println("Resultado: " + golsA + " x " + golsB);

                        if (golsA > golsB) {
                            System.out.println("✓ " + nomeA + " VENCEU!");
                        } else if (golsB > golsA) {
                            System.out.println("✓ " + nomeB + " VENCEU!");
                        } else {
                            System.out.println(" EMPATE!");
                        }

                        conn.close();
                    } catch (Exception e) {
                        System.out.println("✗ ERRO: " + e.getMessage());
                    }
                    break;

                case "5":
                    // VIOLAÇÃO SRP: Lista aqui na Main
                    try {
                        Connection conn = DriverManager.getConnection(URL, USER, PASS);
                        String sql = "SELECT * FROM clube ORDER BY nome";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();

                        System.out.println("\n========== CLUBES CADASTRADOS ==========");
                        while (rs.next()) {
                            System.out.println("ID: " + rs.getInt("id") + " | Nome: " + rs.getString("nome") +
                                             " | Ano: " + rs.getInt("ano_fundacao") + " | País: " + rs.getString("pais"));
                        }

                        conn.close();
                    } catch (Exception e) {
                        System.out.println("✗ ERRO: " + e.getMessage());
                    }
                    break;

                case "6":
                    // VIOLAÇÃO SRP: Lista usuários aqui
                    try {
                        Connection conn = DriverManager.getConnection(URL, USER, PASS);
                        String sql = "SELECT * FROM usuario ORDER BY nome";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();

                        System.out.println("\n========== USUÁRIOS CADASTRADOS ==========");
                        while (rs.next()) {
                            System.out.println("ID: " + rs.getInt("id") + " | Nome: " + rs.getString("nome") +
                                             " | Tipo: " + rs.getString("tipo") + " | Peso: " + rs.getDouble("peso") +
                                             "kg | Multa: R$ " + rs.getDouble("multa"));
                        }

                        conn.close();
                    } catch (Exception e) {
                        System.out.println("✗ ERRO: " + e.getMessage());
                    }
                    break;

                case "7":
                    // VIOLAÇÃO SRP: Deleta clube aqui sem validação
                    System.out.print("ID do Clube para deletar: ");
                    int idClubeDel = 0;
                    try {
                        idClubeDel = s.nextInt();
                        s.nextLine(); // Limpa o buffer
                    } catch (IllegalArgumentException e) {
                        System.out.println("✗ ID do Clube inválido!");
                        s.nextLine();
                        break;
                    }

                    try {
                        Connection conn = DriverManager.getConnection(URL, USER, PASS);
                        String sql = "DELETE FROM clube WHERE id = " + idClubeDel;
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.executeUpdate();

                        System.out.println("✓ Clube deletado!");
                        conn.close();
                    } catch (Exception e) {
                        System.out.println("✗ ERRO: " + e.getMessage());
                    }
                    break;

                case "0":
                    System.out.println("\n Saindo do sistema... Obrigado pelo caos!");
                    continuar = false;
                    break;

                default:
                    System.out.println("✗ Opção inválida! Tente novamente.");
                    break;
            }
        }

        s.close();
    }
}