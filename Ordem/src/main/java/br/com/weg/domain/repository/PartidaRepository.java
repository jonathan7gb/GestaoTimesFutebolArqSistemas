package br.com.weg.domain.repository;

import br.com.weg.domain.entity.Partida;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PartidaRepository {

    Partida criarPartida (Partida partida) throws SQLException;
    List<Partida> listarPartidas () throws SQLException;
    List<Partida> buscarPartidaPorClube(int clubeId) throws SQLException;
    Map<Integer, String> listarNomeClubes() throws SQLException;
}
