package br.com.weg.domain.repository;

import br.com.weg.domain.entity.Clube;

import java.sql.SQLException;
import java.util.List;

public interface ClubeRepository {
    Clube criarClube(Clube clube) throws SQLException;
    List<Clube> listarClubes() throws SQLException;
    Clube existeId(int id) throws SQLException;
}
