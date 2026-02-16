package br.com.weg.presentation.controller;


import br.com.weg.application.service.ClubeService;
import br.com.weg.application.service.PartidaService;
import br.com.weg.application.service.UsuarioService;
import br.com.weg.presentation.view.AdminView;
import br.com.weg.presentation.view.ClubeView;
import br.com.weg.presentation.view.PartidaView;
import br.com.weg.presentation.view.UsuarioView;
import br.com.weg.presentation.view.helpers.MessageHelper;

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

        switch (escolhaPrincipal){
            case 1 -> {
                int escolhaJogador = usuarioView.jogadorMenu();
                System.out.println();
            }
            case 2 -> {
                int escolhaJogador = usuarioView.comissaoMenu();
                System.out.println();
            }
            case 3 -> {
                int escolhaJogador = usuarioView.presidenteMenu();
                System.out.println();
            }
            case 4 -> {
                int escolhaJogador = adminView.adminMenu();
                System.out.println();
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
