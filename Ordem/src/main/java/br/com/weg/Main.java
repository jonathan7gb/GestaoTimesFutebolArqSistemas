package br.com.weg;


import br.com.weg.application.service.ClubeService;
import br.com.weg.application.service.PartidaService;
import br.com.weg.application.service.UsuarioService;
import br.com.weg.presentation.controller.MainController;

public class Main {
    public static void main(String[] args) {

        ClubeService clubeService = new ClubeService();
        PartidaService partidaService = new PartidaService();
        UsuarioService usuarioService = new UsuarioService();

        MainController mainController = new MainController(clubeService, partidaService, usuarioService);

        mainController.startSystem();
    }
}