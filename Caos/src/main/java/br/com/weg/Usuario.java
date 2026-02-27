package br.com.weg;

public class Usuario {
    public int id;
    public String nome;
    public double peso;
    public String tipo; // "JOGADOR", "TECNICO", "PRESIDENTE"
    public int idClube;

    // VIOLAÇÃO DE OCP: Para cada novo tipo, esse método cresce infinitamente
    public void executarAcaoDoTipo() {
        if (tipo.equals("JOGADOR")) {
            System.out.println(nome + " está correndo atrás da bola...");
        } else if (tipo.equals("TECNICO")) {
            System.out.println(nome + " está gritando na beira do campo...");
        } else if (tipo.equals("PRESIDENTE")) {
            System.out.println(nome + " está assinando cheques...");
        }
    }

    // VIOLAÇÃO DE SRP: Model formatando texto para a UI
    public String gerarLinhaRelatorio() {
        return "ID: " + id + " | NOME: " + nome.toUpperCase() + " | TIPO: " + tipo;
    }
}