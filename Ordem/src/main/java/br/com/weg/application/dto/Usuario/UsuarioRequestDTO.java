package br.com.weg.application.dto.Usuario;

public record UsuarioRequestDTO (String nome,
                                 double peso,
                                 int altura,
                                 String tipo,
                                 int idClube){
}
