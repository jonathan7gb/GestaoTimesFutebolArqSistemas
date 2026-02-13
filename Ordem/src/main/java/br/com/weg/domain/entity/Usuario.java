package br.com.weg.domain.entity;

public class Usuario {

    private int id;
    private String nome;
    private double peso;
    private int altura;
    private String tipo;
    private int idClube;

    public Usuario(int id, String nome, double peso, int altura, String tipo, int idClube) {
        this.id = id;
        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.tipo = tipo;
        this.idClube = idClube;
    }

    public Usuario(String nome, double peso, int altura, String tipo, int idClube) {
        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.tipo = tipo;
        this.idClube = idClube;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdClube() {
        return idClube;
    }

    public void setIdClube(int idClube) {
        this.idClube = idClube;
    }
}
