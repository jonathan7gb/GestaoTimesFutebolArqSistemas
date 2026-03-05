package br.com.weg.domain.service;

import br.com.weg.application.dto.Usuario.UsuarioRequestDTO;
import br.com.weg.application.dto.Usuario.UsuarioResponseDTO;

import java.util.List;
import java.util.Map;

/**
 * Interface de serviço para Usuario.
 * Garante o DIP: o controlador depende desta abstração,
 * não da implementação concreta.
 */
public interface IUsuarioService {
    UsuarioResponseDTO criarUsuario(UsuarioRequestDTO uReqDto);
    List<UsuarioResponseDTO> listarUsuarios();
    Map<Integer, String> idsNomeClubeUsuario();
    List<UsuarioResponseDTO> listarUsuariosDeUmClube(int id);
}
