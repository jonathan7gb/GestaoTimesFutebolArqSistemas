package br.com.weg.application.dto.Usuario;

public record UsuarioResponseDTO(int id,
                                 String nome,
                                 double peso,
                                 int altura,
                                 String tipo,
                                 int idClube) {
}
