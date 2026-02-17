package br.com.weg.application.service;

import br.com.weg.application.dto.Usuario.UsuarioRequestDTO;
import br.com.weg.application.dto.Usuario.UsuarioResponseDTO;
import br.com.weg.application.mapper.UsuarioMapper;
import br.com.weg.domain.entity.Usuario;
import br.com.weg.domain.repository.UsuarioRepository;
import br.com.weg.infra.persistence.UsuarioRepositoryImpl;
import br.com.weg.presentation.view.helpers.MessageHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioService {

    UsuarioMapper uMapper = new UsuarioMapper();
    UsuarioRepository uRepository = new UsuarioRepositoryImpl();

    public void criarUsuario(UsuarioRequestDTO uReqDto){
        Usuario u = uMapper.toEntity(uReqDto);
        try{
            u = uRepository.criarUsuario(u);
        } catch (SQLException e) {
            MessageHelper.error("Erro ao salvar usuário!\n");
        }
    }

    public List<UsuarioResponseDTO> listarUsuarios(){
        List<UsuarioResponseDTO> dtoList = new ArrayList<>();

        try{
            List<Usuario> usuarioList = uRepository.listarUsuarios();

            if(usuarioList.isEmpty()){
                MessageHelper.error("Nenhum usuário encontrado!");
            }else{

                for(Usuario u: usuarioList){
                    dtoList.add(uMapper.toDto(u));
                }
            }
        } catch (SQLException e) {
            MessageHelper.error("Erro ao listar os usuários");
        }
        return dtoList;
    }

    public Map<Integer, String> idsNomeClubeUsuario(){
        Map<Integer, String> idsNomeClubeUsuario = new HashMap<>();
        try{
            idsNomeClubeUsuario = uRepository.listarIdENomeClubeUsuario();
        } catch (SQLException e) {
            MessageHelper.error("Erro ao listar os usuários");
        }
        return idsNomeClubeUsuario;
    }
}
