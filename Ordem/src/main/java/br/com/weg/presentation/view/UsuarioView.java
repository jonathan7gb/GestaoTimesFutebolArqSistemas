package br.com.weg.presentation.view;

import br.com.weg.presentation.view.helpers.InputHelper;

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
}
