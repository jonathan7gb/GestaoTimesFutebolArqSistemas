package br.com.weg.infra.persistence;

import br.com.weg.domain.entity.Clube;
import br.com.weg.domain.repository.ClubeRepository;
import br.com.weg.infra.database.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubeRepositoryImpl implements ClubeRepository {


    @Override
    public Clube criarClube(Clube clube) throws SQLException {

        String sql = """
                INSERT INTO clube (
                nome, 
                ano_fundacao,
                pais )
                VALUES (?,?,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, clube.getNome());
            ps.setInt(2, clube.getAnoFundacao());
            ps.setString(3, clube.getPais());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()){
                int idGerado = rs.getInt(1);
                clube.setId(idGerado);
            }
        }

        return clube;
    }

    @Override
    public List<Clube> listarClubes() throws SQLException {

        List<Clube>clubes = new ArrayList<>();

        String sql = """
                SELECT 
                id, 
                nome, 
                ano_fundacao,
                pais
                FROM clube
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Clube clube = new Clube(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("ano_fundacao"),
                        rs.getString("pais")
                );
                clubes.add(clube);
            }
        }
        return clubes;
    }

    @Override
    public Clube existeId(int id) throws SQLException {

        String sql = """
                SELECT 
                id, 
                nome, 
                ano_fundacao,
                pais
                WHERE id = ? 
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int idBanco = rs.getInt("id");
                String nome = rs.getString("nome");
                int anoFundacao = rs.getInt("ano_fundacao");
                String pais = rs.getString("pais");

                return new Clube(idBanco, nome, anoFundacao, pais);
            }
        }
        return null;
    }
}
