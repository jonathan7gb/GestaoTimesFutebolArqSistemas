package br.com.weg.presentation.view;

import br.com.weg.application.dto.Clube.ClubeRequestDTO;
import br.com.weg.presentation.view.helpers.InputHelper;

import java.util.Scanner;

public class AdminView {

    Scanner sc = new Scanner(System.in);

    public int menuPrincipal(){
        System.out.println("""
                | ====== MENU PRINCIPAL ====== |
                | 1 - MENU JOGADOR             |
                | 2 - MENU COMISSÃO TÉCNICA    |
                | 3 - MENU PRESIDENTE          |
                | 4 - MENU ADMIN               |
                | ============================ |
                | 0 - SAIR DO SISTEMA          |
                | ============================ |""");
        return InputHelper.inputInteger("| ? - SUA ESCOLHA: ", sc);
    }

    public int adminMenu(){
        System.out.println("""
                | ====== MENU ADMIN ====== |
                | 1 - CRIAR CLUBE          |
                | 2 - CRIAR USUARIO        |
                | 3 - CRIAR PARTIDA        |
                | 4 - LISTAR CLUBES        |
                | 5 - LISTAR USUARIOS      |
                | 6 - LISTAR PARTIDAS      |
                | ======================== |
                | 0 - SAIR DO SISTEMA      |
                | ======================== |""");
        return InputHelper.inputInteger("| ? - SUA ESCOLHA: ", sc);
    }
}
