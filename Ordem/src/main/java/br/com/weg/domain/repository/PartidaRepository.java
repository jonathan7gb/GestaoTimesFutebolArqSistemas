package br.com.weg.domain.repository;

import br.com.weg.domain.entity.Partida;

import java.sql.SQLException;
import java.util.List;

public interface PartidaRepository {

    Partida criarPartida (Partida partida) throws SQLException;
    List<Partida> listarPartidas () throws SQLException;
    void buscarPartidaPorClube(int id) throws SQLException;
}
