package br.com.weg.presentation.view;

import br.com.weg.application.dto.Clube.ClubeRequestDTO;
import br.com.weg.application.dto.Clube.ClubeResponseDTO;
import br.com.weg.presentation.view.helpers.InputHelper;

import java.util.List;
import java.util.Scanner;

public class ClubeView {

    Scanner sc = new Scanner(System.in);

    public ClubeRequestDTO criarClube(){
        System.out.println("\n| ====== CRIAR CLUBE ====== |");
        String nome = InputHelper.inputString("| Insira o nome do clube: ", sc);
        int anoFundacao = InputHelper.inputInteger("| Insira o ano de fundação do clube("+ nome +"): ", sc);
        String pais = InputHelper.inputString("| Insira o país de origem do clube(" + nome + "): ", sc);
        return new ClubeRequestDTO(nome, anoFundacao, pais);
    }

    public void listarClubes(List<ClubeResponseDTO> dtoList){
        for(ClubeResponseDTO c : dtoList){
            mostrarClube(c);
        }
    }

    public void mostrarClube(ClubeResponseDTO clubeResponseDTO){
        System.out.println("| =========================== |");
        System.out.println("| ID: " + clubeResponseDTO.id());
        System.out.println("| NOME: " + clubeResponseDTO.nome());
        System.out.println("| ANO FUNDAÇÃO: " + clubeResponseDTO.anoFundacao());
        System.out.println("| PAÍS: " + clubeResponseDTO.pais());
        System.out.println("| =========================== |");
    }
}
