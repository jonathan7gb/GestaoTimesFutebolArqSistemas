package br.com.weg.application.mapper;

import br.com.weg.application.dto.Usuario.UsuarioRequestDTO;
import br.com.weg.application.dto.Usuario.UsuarioResponseDTO;
import br.com.weg.domain.entity.Usuario;

public class UsuarioMapper {

    public UsuarioResponseDTO toDto(Usuario usuario){
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getPeso(), usuario.getAltura(), usuario.getTipo(), usuario.getIdClube());
    }

    public Usuario toEntity(UsuarioRequestDTO requestDTO){
        return new Usuario(requestDTO.nome(), requestDTO.peso(), requestDTO.altura(), requestDTO.tipo(), requestDTO.idClube());
    }
}
