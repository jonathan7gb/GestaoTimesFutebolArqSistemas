package br.com.weg.presentation.view;

import br.com.weg.application.dto.Clube.ClubeRequestDTO;
import br.com.weg.application.dto.Clube.ClubeResponseDTO;
import br.com.weg.application.dto.Partida.PartidaRequestDTO;
import br.com.weg.application.dto.Partida.PartidaResponseDTO;
import br.com.weg.presentation.view.helpers.InputHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PartidaView {

    Scanner sc = new Scanner(System.in);

    public PartidaRequestDTO criarPartida(PartidaRequestDTO partidaRequestDTO){
        System.out.println("\n| ====== CRIAR PARTIDA ====== |");
        int clubeA = InputHelper.inputInteger("| Insira o id do 1º clube: ", sc);
        int clubeB = InputHelper.inputInteger("| Insira o id do 2º clube: ", sc);
        LocalDate dataHora = InputHelper.inputDate("| Insira a data da partida: ", sc);
        String localizacao = InputHelper.inputString("| Insira a localização da partida: ", sc);
        return new PartidaRequestDTO(clubeA, clubeB, dataHora, localizacao);
    }

    public PartidaRequestDTO criarPartidaPresidente(PartidaRequestDTO partidaRequestDTO, int idClubeA){
        System.out.println("\n| ====== CRIAR PARTIDA ====== |");
        int clubeB = InputHelper.inputInteger("| Insira o id do clube adversário: ", sc);
        LocalDate dataHora = InputHelper.inputDate("| Insira a data da partida: ", sc);
        String localizacao = InputHelper.inputString("| Insira a localização da partida: ", sc);
        return new PartidaRequestDTO(idClubeA, clubeB, dataHora, localizacao);
    }

    public void listarPartidas(List<PartidaResponseDTO> dtoList , String nomeClubeA, String nomeClubeB){
        for(PartidaResponseDTO c : dtoList){
            mostrarPartida(c, nomeClubeA, nomeClubeB);
        }
    }

    public void mostrarPartida(PartidaResponseDTO partidaResponseDTO, String nomeClubeA, String nomeClubeB){
        System.out.println("| ============================= |");
        System.out.println("| " + nomeClubeA + " x " + nomeClubeB);
        System.out.println("| " + partidaResponseDTO.dataHoraPartida() + " | " + partidaResponseDTO.localizacao());
        System.out.println("| ============================= |");
    }
}
