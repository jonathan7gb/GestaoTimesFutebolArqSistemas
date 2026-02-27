package br.com.weg.presentation.controller;


import br.com.weg.application.dto.Clube.ClubeRequestDTO;
import br.com.weg.application.dto.Partida.PartidaRequestDTO;
import br.com.weg.application.dto.Partida.PartidaResponseDTO;
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

    public void startSystem() {
        while (true) {
            int escolhaPrincipal = adminView.menuPrincipal();
            System.out.println();

            switch (escolhaPrincipal) {
                case 1 -> menuJogador();
                case 2 -> menuComissao();
                case 3 -> menuPresidente();
                case 4 -> menuAdmin();
                case 0 -> {
                    return;
                }
                default -> MessageHelper.error("Escolha inválida");
            }
        }
    }

    private void menuJogador() {
        while (true) {
            int escolha = usuarioView.jogadorMenu();
            if (escolha == 0) break;

            switch (escolha) {
                case 1 -> {
                    int idClube = clubeView.pedirIdClube();
                    usuarioView.listarUsuarios(usuarioService.listarUsuariosDeUmClube(idClube), usuarioService.idsNomeClubeUsuario());
                }
                case 2 -> {
                    int idClube = clubeView.pedirIdClube();
                    partidaView.listarPartidas(partidaService.listarPartidasPorClube(idClube), partidaService.retornarNomesClubesPartidas());
                }
            }
        }
    }

    private void menuComissao() {
        while (true) {
            int escolha = usuarioView.comissaoMenu();
            if (escolha == 0) break;

            switch (escolha) {
                case 1 -> {
                    int idClube = clubeView.pedirIdClube();
                    usuarioView.listarUsuarios(usuarioService.listarUsuariosDeUmClube(idClube), usuarioService.idsNomeClubeUsuario());
                }
                case 2 -> {
                    int idClube = clubeView.pedirIdClube();
                    partidaView.listarPartidas(partidaService.listarPartidasPorClube(idClube), partidaService.retornarNomesClubesPartidas());
                }
            }
        }
    }

    private void menuPresidente() {
        while (true) {
            int escolha = usuarioView.presidenteMenu();
            if (escolha == 0) break;

            switch (escolha) {
                case 1 -> usuarioService.criarUsuario(usuarioView.criarUsuarioPresidente());
                case 2 -> partidaService.criarPartida(partidaView.criarPartida());
                case 3 -> {
                    int idClube = clubeView.pedirIdClube();
                    usuarioView.listarUsuarios(usuarioService.listarUsuariosDeUmClube(idClube), usuarioService.idsNomeClubeUsuario());
                }
                case 4 -> {
                    int idClube = clubeView.pedirIdClube();
                    partidaView.listarPartidas(partidaService.listarPartidasPorClube(idClube), partidaService.retornarNomesClubesPartidas());
                }
            }
        }
    }

    private void menuAdmin() {
        while (true) {
            int escolha = adminView.adminMenu();
            if (escolha == 0) break;

            switch (escolha) {
                case 1 -> clubeService.criarClube(clubeView.criarClube());
                case 2 -> usuarioService.criarUsuario(usuarioView.criarUsuario());
                case 3 -> partidaService.criarPartida(partidaView.criarPartida());
                case 4 -> clubeView.listarClubes(clubeService.mostrarClubes());
                case 5 -> usuarioView.listarUsuarios(usuarioService.listarUsuarios(), usuarioService.idsNomeClubeUsuario());
                case 6 -> partidaView.listarPartidas(partidaService.listarPartidas(), partidaService.retornarNomesClubesPartidas());
            }
        }
    }
}
