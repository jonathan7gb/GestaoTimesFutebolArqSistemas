package br.com.weg.domain.entity;

import java.time.LocalDate;

public class Partida {

    private int id;
    private int idClubeA;
    private int idClubeB;
    private LocalDate dataHoraPartida;
    private String localizacao;

    public Partida(int id, int idClubeA, int idClubeB, LocalDate dataHoraPartida, String localizacao) {
        this.id = id;
        this.idClubeA = idClubeA;
        this.idClubeB = idClubeB;
        this.dataHoraPartida = dataHoraPartida;
        this.localizacao = localizacao;
    }

    public Partida(int idClubeA, int idClubeB, LocalDate dataHoraPartida, String localizacao) {
        this.idClubeA = idClubeA;
        this.idClubeB = idClubeB;
        this.dataHoraPartida = dataHoraPartida;
        this.localizacao = localizacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClubeA() {
        return idClubeA;
    }

    public void setIdClubeA(int idClubeA) {
        this.idClubeA = idClubeA;
    }

    public int getIdClubeB() {
        return idClubeB;
    }

    public void setIdClubeB(int idClubeB) {
        this.idClubeB = idClubeB;
    }

    public LocalDate getDataHoraPartida() {
        return dataHoraPartida;
    }

    public void setDataHoraPartida(LocalDate dataHoraPartida) {
        this.dataHoraPartida = dataHoraPartida;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
