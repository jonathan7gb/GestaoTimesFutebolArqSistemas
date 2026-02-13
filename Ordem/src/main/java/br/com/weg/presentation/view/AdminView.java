package br.com.weg.presentation.view;

import br.com.weg.application.dto.Clube.ClubeRequestDTO;
import br.com.weg.presentation.view.helpers.InputHelper;

import java.util.Scanner;

public class AdminView {

    Scanner sc = new Scanner(System.in);

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
