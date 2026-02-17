package br.com.weg.infra.persistence;

import br.com.weg.domain.entity.Usuario;
import br.com.weg.domain.enums.TipoUsuario;
import br.com.weg.domain.repository.UsuarioRepository;
import br.com.weg.infra.database.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioRepositoryImpl implements UsuarioRepository {


    @Override
    public Usuario criarUsuario(Usuario usuario) throws SQLException {

        String sql = """
                INSERT INTO usuario (
                nome, 
                peso,
                altura,
                tipo,
                id_clube )
                VALUES (?,?,?,?::tipo_usuario,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNome());
            ps.setDouble(2, usuario.getPeso());
            ps.setInt(3, usuario.getAltura());
            ps.setString(4, usuario.getTipo().name());
            ps.setInt(5, usuario.getIdClube());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int idGerado = rs.getInt(1);
                usuario.setId(idGerado);
            }
        }
        return usuario;
    }

    @Override
    public List<Usuario> listarUsuarios() throws SQLException {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                
                SELECT 
                id,
                nome,
                peso,
                altura,
                tipo,
                id_clube 
                FROM
                usuario
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        TipoUsuario.valueOf(rs.getString("tipo")),
                        rs.getInt("id_clube")
                );
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    @Override
    public List<Usuario> listarJogador() throws SQLException {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT 
                id,
                nome,
                peso, 
                altura,
                id_clube
                FROM 
                usuario
                WHERE tipo = 'JOGADOR'
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        rs.getInt("id_clube")
                );
                usuarios.add(usuario);
                return usuarios;
            }
        }

        return List.of();
    }

    @Override
    public List<Usuario> listarComissaoTecnica() throws SQLException {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT 
                id,
                nome,
                peso, 
                altura,
                id_clube
                FROM 
                usuario
                WHERE tipo = 'COMISSAOTECNICA'
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        rs.getInt("id_clube")
                );
                usuarios.add(usuario);
                return usuarios;
            }

            return List.of();
        }

    }

    @Override
    public List<Usuario> listarPresidente() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT 
                id,
                nome,
                peso, 
                altura,
                id_clube
                FROM 
                usuario
                WHERE tipo = 'PRESIDENTE'
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        rs.getInt("id_clube")
                );
                usuarios.add(usuario);
                return usuarios;
            }

            return List.of();
        }
    }

    @Override
    public Usuario buscarJogadorPorId(int id) throws SQLException {

        String sql = """
                SELECT 
                id,
                nome,
                peso,
                altura,
                id_clube
                FROM usuario
                WHERE id = ? AND tipo = 'JOGADOR'
                """;

        try(Connection conn = Conexao.conectar();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        rs.getInt("id_clube")
                );
                return usuario;
            }
        }

        return null;
    }

    @Override
    public List<Usuario> listarJogadorPorClube(int clubeId) throws SQLException {
        List<Usuario> jogadores = new ArrayList<>();

        String sql =  """
                SELECT u.id, u.nome, u.peso, u.altura, c.nome AS nome_clube
                FROM usuario u
                JOIN clube c ON u.id_clube = c.id
                WHERE u.id_clube = ? AND u.tipo = 'Jogador'
                """;

        try(Connection conn = Conexao.conectar();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, clubeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Usuario jogador = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        rs.getInt("id_clube")
                );

                jogadores.add(jogador);
                return jogadores;
            }
        }
        return null;
    }

    @Override
    public Map<Integer, String> listarIdENomeClubeUsuario() throws SQLException{
        Map<Integer, String> listaIdENomeClube = new HashMap<>();

        String sql = """
                SELECT u.id, c.nome
                FROM usuario u
                JOIN clube c ON u.id_clube = c.id
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
               listaIdENomeClube.put(rs.getInt(1), rs.getString(2));
            }
        }
        return listaIdENomeClube;
    }

    @Override
    public boolean verificarSeNomeJaExisteNoClube(String nome, int id_clube) throws SQLException{
        String sql = """
                SELECT nome
                FROM usuario
                WHERE nome = ?
                AND id_clube = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, nome);
            ps.setInt(2, id_clube);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return true;
            }
        }

        return false;
    }
}
