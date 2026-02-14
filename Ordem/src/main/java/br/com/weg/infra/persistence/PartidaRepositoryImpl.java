package br.com.weg.infra.persistence;

import br.com.weg.domain.entity.Partida;
import br.com.weg.domain.repository.PartidaRepository;
import br.com.weg.infra.database.Conexao;

import java.net.ConnectException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PartidaRepositoryImpl implements PartidaRepository {
    @Override
    public Partida criarPartida(Partida partida) throws SQLException {

        String sql = """
                INSERT INTO partida (
                id_clube_a,
                id_clube_b,
                data_hora,
                local )
                VALUES (?,?,?,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setInt(1, partida.getIdClubeA());
            ps.setInt(2, partida.getIdClubeB());
            ps.setObject(3, partida.getDataHoraPartida());
            ps.setString(4, partida.getLocalizacao());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()){
                int idGerado = rs.getInt(1);
                partida.setId(idGerado);
            }

        }
        return partida;
    }

    @Override
    public List<Partida> listarPartidas() throws SQLException {

        List<Partida>partidas = new ArrayList<>();

        String sql = """
                SELECT
                id,
                id_clube_a,
                id_clube_b,
                data_hora,
                local
                FROM partida
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Partida partida = new Partida(
                        rs.getInt("id"),
                        rs.getInt(" id_clube_a"),
                        rs.getInt(" id_clube_b"),
                        rs.getObject("data_hora", LocalDateTime.class),
                        rs.getString("local")
                );

                partidas.add(partida);
            }
        }

        return partidas;
    }

    @Override
    public List<Partida> buscarPartidaPorClube(int clubeId) throws SQLException {

        List<Partida>partidas = new ArrayList<>();

        String sql = """
                SELECT
                id,
                id_clube_a,
                id_clube_b,
                data_hora,
                local
                FROM partidas 
                WHERE id_clube_a = ? OR id_clube_b = ?
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Partida partida = new Partida(
                        rs.getInt("id"),
                        rs.getInt(" id_clube_a"),
                        rs.getInt(" id_clube_b"),
                        rs.getObject("data_hora", LocalDateTime.class),
                        rs.getString("local")
                );
                partidas.add(partida);
            }
        }
        return partidas;
    }
}
