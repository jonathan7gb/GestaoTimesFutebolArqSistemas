package br.com.weg.domain.repository;

import br.com.weg.domain.entity.Usuario;
import br.com.weg.domain.enums.TipoUsuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UsuarioRepository {

    Usuario criarUsuario (Usuario usuario) throws SQLException;

    List<Usuario> listarUsuarios() throws SQLException;

    List<Usuario> listarJogador() throws SQLException;

    List<Usuario> listarComissaoTecnica() throws SQLException;

    List<Usuario> listarPresidente() throws SQLException;

    Usuario buscarJogadorPorId(int id) throws SQLException;

    List<Usuario> listarJogadorPorClube (int clubeId) throws SQLException;

    Map<Integer, String> listarIdENomeClubeUsuario() throws SQLException;

    boolean verificarSeNomeJaExisteNoClube(String nome, int id_clube) throws SQLException;
}
