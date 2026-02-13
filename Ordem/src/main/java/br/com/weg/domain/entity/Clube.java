package br.com.weg.domain.entity;

public class Clube {

    private int id;
    private String nome;
    private int anoFundacao;
    private String pais;

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

    public int getAnoFundacao() {
        return anoFundacao;
    }

    public void setAnoFundacao(int anoFundacao) {
        this.anoFundacao = anoFundacao;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
