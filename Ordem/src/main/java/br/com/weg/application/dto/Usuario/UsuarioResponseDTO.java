package br.com.weg.application.dto.Usuario;

import br.com.weg.domain.enums.TipoUsuario;

public record UsuarioResponseDTO(int id,
                                 String nome,
                                 double peso,
                                 int altura,
                                 TipoUsuario tipo,
                                 int idClube) {
}
