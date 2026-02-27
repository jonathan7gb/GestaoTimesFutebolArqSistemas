package br.com.weg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // VIOLAÇÃO DE DIP: Dependência direta de Scanner e Conexão Hardcoded
        Scanner s = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/gestao_futebol_caos?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "kauafelix123@";

        System.out.println("!!! BEM-VINDO AO SISTEMA DE GESTÃO DO CAOS FC !!!");

        while (true) {
            System.out.println("\nESCOLHA O DESASTRE: 1-Add Clube | 2-Add Jogador | 3-Pagar | 4-Simular Jogo | 0-Sair");
            String op = s.next();

            // VIOLAÇÃO DE SRP: A Main controla o fluxo, o banco, a lógica e o erro
            if (op.equals("1")) {
                Clube c = new Clube(); // Instanciação direta (new)
                System.out.print("Nome do Time: "); c.nome = s.next(); // Atributo público!
                System.out.print("País: "); c.pais = s.next();

                Connection conn = DriverManager.getConnection(url, user, pass);
                String sql = "INSERT INTO clube (nome, pais) VALUES ('" + c.nome + "', '" + c.pais + "')";
                PreparedStatement ps = conn.prepareStatement(sql);
                System.out.println("Clube inserido via String concatenada! (Boa sorte com as aspas)");
                conn.close();

            } else if (op.equals("2")) {
                Usuario u = new Usuario();
                System.out.print("Nome: "); u.nome = s.next();
                System.out.print("Peso: "); u.peso = s.nextDouble();
                System.out.print("Tipo (JOGADOR/PRESIDENTE): "); u.tipo = s.next();

                // VIOLAÇÃO DE OCP: Lógica de negócio em ifs infinitos
                if (u.tipo.equals("JOGADOR")) {
                    if (u.peso > 120) {
                        System.out.println("JOGADOR FORA DE FORMA! Aplicando multa de R$ 500 no banco agora...");
                        Connection conn = DriverManager.getConnection(url, user, pass);
                        PreparedStatement ps = conn.prepareStatement("UPDATE usuario SET multa = 500 WHERE nome = '" + u.nome + "'");
                    }
                }

                Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = conn.prepareStatement("INSERT INTO usuario (nome, tipo) VALUES ('" + u.nome + "', '" + u.tipo + "')");
                conn.close();

            } else if (op.equals("3")) {
                // VIOLAÇÃO DE STRATEGY: Em vez de um padrão para cálculo de bônus...
                System.out.println("Calcular bônus para: 1-Atacante | 2-Goleiro | 3-Técnico");
                int t = s.nextInt();
                double bonus = 0;

                switch (t) {
                    case 1: bonus = 1000 * 2; break;
                    case 2: bonus = 500 + 100; break;
                    case 3: bonus = 5000 / 2; break;
                    default: bonus = 1;
                }
                System.out.println("Bônus calculado no 'olhômetro': " + bonus);

            } else if (op.equals("4")) {
                // VIOLAÇÃO DE LSP: Vamos tentar fazer o Presidente jogar.
                System.out.println("Iniciando partida... Chamando o Presidente para bater o pênalti!");

                MembroFutebol pres = new MembroFutebol() {
                    public void jogar() { throw new RuntimeException("ERRO FATAL: Presidente infartou tentando correr!"); }
                    public void treinar() { System.out.println("Presidente olhando o treino de terno."); }
                    public void contratar() { System.out.println("Contratando..."); }
                    public void demitir() { System.out.println("Demitindo..."); }
                    public void cobrarEscanteio() { throw new RuntimeException("Erro: Sapato social não chuta bola!"); }
                };

                pres.jogar();

            } else if (op.equals("0")) {
                break;
            } else {
                System.out.println("Opção que não existe, mas o sistema nem valida direito.");
            }
        }
    }
}