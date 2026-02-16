package br.com.weg.presentation.controller;


import br.com.weg.application.service.ClubeService;
import br.com.weg.application.service.PartidaService;
import br.com.weg.application.service.UsuarioService;

public class MainController {

    ClubeService clubeService = new ClubeService();
    PartidaService partidaService = new PartidaService();
    UsuarioService usuarioService = new UsuarioService();

    public MainController(ClubeService clubeService, PartidaService partidaService, UsuarioService usuarioService){
        this.clubeService = clubeService;
        this.partidaService = partidaService;
        this.usuarioService = usuarioService;
    }

    public void startSystem(){

    }
}
