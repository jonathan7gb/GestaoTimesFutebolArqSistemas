package br.com.weg.presentation.controller;


import br.com.weg.application.dto.Clube.ClubeRequestDTO;
import br.com.weg.application.dto.Partida.PartidaRequestDTO;
import br.com.weg.application.dto.Usuario.UsuarioRequestDTO;
import br.com.weg.application.dto.Usuario.UsuarioResponseDTO;
import br.com.weg.application.service.ClubeService;
import br.com.weg.application.service.PartidaService;
import br.com.weg.application.service.UsuarioService;
import br.com.weg.presentation.view.AdminView;
import br.com.weg.presentation.view.ClubeView;
import br.com.weg.presentation.view.PartidaView;
import br.com.weg.presentation.view.UsuarioView;
import br.com.weg.presentation.view.helpers.MessageHelper;

import java.util.List;

public class MainController {

    ClubeService clubeService = new ClubeService();
    PartidaService partidaService = new PartidaService();
    UsuarioService usuarioService = new UsuarioService();
    AdminView adminView = new AdminView();
    ClubeView clubeView = new ClubeView();
    PartidaView partidaView = new PartidaView();
    UsuarioView usuarioView = new UsuarioView();

    public MainController(ClubeService clubeService, PartidaService partidaService, UsuarioService usuarioService){
        this.clubeService = clubeService;
        this.partidaService = partidaService;
        this.usuarioService = usuarioService;
    }

    public void startSystem(){
        int escolhaPrincipal = adminView.menuPrincipal();
        System.out.println();

        switch (escolhaPrincipal){
            case 1 -> {
                int escolhaJogador = usuarioView.jogadorMenu();
                System.out.println();

            }
            case 2 -> {
                int escolhaComissao = usuarioView.comissaoMenu();
                System.out.println();
            }
            case 3 -> {
                do{
                    int escolhaPresidente = usuarioView.presidenteMenu();
                    System.out.println();
                    switch (escolhaPresidente){
                        case 1 -> {
                            UsuarioRequestDTO usuarioRequestDTO = usuarioView.criarUsuarioPresidente();
                            usuarioService.criarUsuario(usuarioRequestDTO);
                        }
                        case 2 -> {
                            PartidaRequestDTO partidaRequestDTO = partidaView.criarPartida();
                            partidaService.criarPartida(partidaRequestDTO);
                        }
                        case 3 -> {
                            int idClube = clubeView.pedirIdClube();
                            List<UsuarioResponseDTO>listarUsuariosPorClube = usuarioService.listarUsuariosDeUmClube(idClube);
                            usuarioView.listarUsuarios(listarUsuariosPorClube,  usuarioService.idsNomeClubeUsuario());
                        }
                        case 0 -> {
                            return;
                        }
                    }
                }while(true);
            }
            case 4 -> {
                do{
                    int escolhaAdmin = adminView.adminMenu();
                    System.out.println();
                    switch (escolhaAdmin){
                        case 1 -> {
                           ClubeRequestDTO clubeRequestDTO =  clubeView.criarClube();
                           clubeService.criarClube(clubeRequestDTO);
                        }
                        case 2 -> {
                            UsuarioRequestDTO requestDTO = usuarioView.criarUsuario();
                            usuarioService.criarUsuario(requestDTO);
                        }
                        case 3 -> {
                            PartidaRequestDTO partidaRequestDTO = partidaView.criarPartida();
                            partidaService.criarPartida(partidaRequestDTO);
                        }
                        case 4 -> {
                            clubeView.listarClubes(clubeService.mostrarClubes());
                            System.out.println();
                        }
                        case 5 -> {
                            usuarioView.listarUsuarios(usuarioService.listarUsuarios(), usuarioService.idsNomeClubeUsuario());
                            System.out.println();
                        }
                        case 6 -> {
                            partidaView.listarPartidas(partidaService.listarPartidas(), partidaService.retornarNomesClubesPartidas());
                            System.out.println();
                        }
                        case 0 -> {
                            return;
                        }
                    }
                }while(true);
            }
            case 0 -> {
                return;
            }
            default -> {
                MessageHelper.error("Escolha inválida");
            }
        }
    }
}
