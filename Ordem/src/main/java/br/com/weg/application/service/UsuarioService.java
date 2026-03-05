package br.com.weg.application.service;

import br.com.weg.application.dto.Usuario.UsuarioRequestDTO;
import br.com.weg.application.dto.Usuario.UsuarioResponseDTO;
import br.com.weg.application.mapper.UsuarioMapper;
import br.com.weg.domain.entity.Usuario;
import br.com.weg.domain.notification.INotificacao;
import br.com.weg.domain.repository.UsuarioRepository;
import br.com.weg.domain.service.IUsuarioService;
import br.com.weg.infra.notification.ConsoleNotificacao;
import br.com.weg.infra.persistence.UsuarioRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioService implements IUsuarioService {

    private final UsuarioMapper uMapper = new UsuarioMapper();
    private final UsuarioRepository uRepository;
    private INotificacao notificacao;

    public UsuarioService() {
        this.uRepository = new UsuarioRepositoryImpl();
        this.notificacao = new ConsoleNotificacao();
    }

    public UsuarioService(UsuarioRepository uRepository, INotificacao notificacao) {
        this.uRepository = uRepository;
        this.notificacao = notificacao;
    }

    public void setNotificacao(INotificacao notificacao) {
        this.notificacao = notificacao;
    }

    @Override
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO uReqDto) {
        Usuario u = uMapper.toEntity(uReqDto);
        try {
            boolean nomeExiste = uRepository.verificarSeNomeJaExisteNoClube(u.getNome(), u.getIdClube());

            if (nomeExiste) {
                notificacao.erro("Usuário com nome já existente nesse clube!\n");
                return null;
            } else {
                u = uRepository.criarUsuario(u);
                notificacao.sucesso("Usuário cadastrado com sucesso!\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            notificacao.erro("Erro ao salvar usuário!\n");
        }

        return uMapper.toDto(u);
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        List<UsuarioResponseDTO> dtoList = new ArrayList<>();

        try {
            List<Usuario> usuarioList = uRepository.listarUsuarios();

            if (usuarioList.isEmpty()) {
                notificacao.erro("Nenhum usuário encontrado!");
            } else {
                for (Usuario u : usuarioList) {
                    dtoList.add(uMapper.toDto(u));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            notificacao.erro("Erro ao listar os usuários");
        }
        return dtoList;
    }

    @Override
    public Map<Integer, String> idsNomeClubeUsuario() {
        Map<Integer, String> idsNomeClubeUsuario = new HashMap<>();
        try {
            idsNomeClubeUsuario = uRepository.listarIdENomeClubeUsuario();
        } catch (SQLException e) {
            e.printStackTrace();
            notificacao.erro("Erro ao listar os usuários");
        }
        return idsNomeClubeUsuario;
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuariosDeUmClube(int id) {
        List<UsuarioResponseDTO> usuarioResponseDTO = new ArrayList<>();
        try {
            List<Usuario> usuarioList = uRepository.listarJogadorPorClube(id);

            if (usuarioList.isEmpty()) {
                notificacao.erro("Nenhum usuário encontrado/Id do clube inválido!");
            } else {
                for (Usuario u : usuarioList) {
                    usuarioResponseDTO.add(uMapper.toDto(u));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            notificacao.erro("Erro ao listar os usuários");
        }

        return usuarioResponseDTO;
    }
}
