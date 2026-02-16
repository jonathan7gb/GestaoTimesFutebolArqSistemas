package br.com.weg.infra.persistence;

import br.com.weg.domain.entity.Usuario;
import br.com.weg.domain.enums.TipoUsuario;
import br.com.weg.domain.repository.UsuarioRepository;
import br.com.weg.infra.database.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                VALUES (?,?,?,?,?)
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, usuario.getNome());
            ps.setDouble(2, usuario.getPeso());
            ps.setInt(3, usuario.getAltura());
            ps.setString(4, usuario.getTipo().name());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()){
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

        try(Connection conn = Conexao.conectar();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        (TipoUsuario) rs.getObject("tipo"),
                        rs.getInt("id_clube")
                );
                usuarios.add(usuario);
                return usuarios;
            }
        }

        return List.of();
    }

    @Override
    public List<Usuario> listarJogador(TipoUsuario tipoUsuario) throws SQLException {

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
                WHERE tipo = ?::user_type
                """;
        try (Connection conn = Conexao.conectar();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, tipoUsuario.name());

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        (TipoUsuario) rs.getObject("tipo"),
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
        return List.of();
    }

    @Override
    public List<Usuario> listarPresidente() throws SQLException {
        return List.of();
    }
}
