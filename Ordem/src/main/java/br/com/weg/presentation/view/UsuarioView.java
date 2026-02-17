package br.com.weg.presentation.view;

import br.com.weg.application.dto.Clube.ClubeResponseDTO;
import br.com.weg.application.dto.Usuario.UsuarioRequestDTO;
import br.com.weg.application.dto.Usuario.UsuarioResponseDTO;
import br.com.weg.domain.enums.TipoUsuario;
import br.com.weg.presentation.view.helpers.InputHelper;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UsuarioView {

    Scanner sc = new Scanner(System.in);

    public int jogadorMenu(){
        System.out.println("""
                | ====== MENU JOGADOR ====== |
                | 1 - LISTAR COMPANHEIROS    |
                | 2 - LISTAR PARTIDAS        |
                | ========================== |
                | 0 - SAIR DO SISTEMA        |
                | ========================== |""");
        return InputHelper.inputInteger("| ? - SUA ESCOLHA: ", sc);
    }

    public int comissaoMenu(){
        System.out.println("""
                | ====== MENU COMISSÃO TÉCNICA ====== |
                | 1 - LISTAR JOGADORES                |
                | 2 - LISTAR PARTIDAS                 |
                | =================================== |
                | 0 - SAIR DO SISTEMA                 |
                | =================================== |""") ;
        return InputHelper.inputInteger("| ? - SUA ESCOLHA: ", sc);
    }

    public int presidenteMenu(){
        System.out.println("""
                | ====== MENU PRESIDENTE ====== |
                | 1 - ADICIONAR JOGADORES       |
                | 2 - MARCAR PARTIDA            |
                | 3 - LISTAR JOGADORES          |
                | 4 - LISTAR PARTIDAS           |
                | ============================= |
                | 0 - SAIR DO SISTEMA           |
                | ============================= |""") ;
        return InputHelper.inputInteger("| ? - SUA ESCOLHA: ", sc);
    }

    public UsuarioRequestDTO criarUsuario(){
        TipoUsuario tipoUsuario = null;
        System.out.println("| ==== CRIAR USUÁRIO ==== |");
        String nome = InputHelper.inputString("| Nome: ", sc);
        double peso = InputHelper.inputDouble("| Peso: ", sc);
        int altura = InputHelper.inputInteger("| Altura (cm): ", sc);
        int tipo = InputHelper.inputInteger("""
                | Tipo de Usuário
                | 1 - JOGADOR
                | 2 - COMISSÃO TÉCNICA
                | 3 - PRESIDENTE
                | 4 - ADMIN
                | Insira o tipo do usuário: """, sc);

        try {
            tipoUsuario = TipoUsuario.values()[tipo - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Escolha inválida");
        }
        int idClube = InputHelper.inputInteger("| Id do clube: ", sc);
        return  new UsuarioRequestDTO(nome, peso, altura, tipoUsuario, idClube);
    }

    public void mostrarUsuario(UsuarioResponseDTO usuarioResponseDTO, String nomeClube){
        System.out.println("| =========================== |");
        System.out.println("| ID: " + usuarioResponseDTO.id());
        System.out.println("| NOME: " + usuarioResponseDTO.nome());
        System.out.println("| PESO: " + usuarioResponseDTO.peso());
        System.out.println("| ALTURA: " + usuarioResponseDTO.altura());
        System.out.println("| TIPO: " + usuarioResponseDTO.tipo().name());
        System.out.println("| CLUBE: " + nomeClube);
        System.out.println("| =========================== |");
    }

    public void listarUsuarios(List<UsuarioResponseDTO> usuarioResponseDTOS, Map<Integer, String> nomeClubes){
        for(UsuarioResponseDTO u : usuarioResponseDTOS){
            String nomeClube = nomeClubes.get(u.id());
            mostrarUsuario(u, nomeClube);
        }
    }
}
