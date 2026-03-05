package br.com.weg.infra.strategies;

import br.com.weg.application.service.ClubeService;
import br.com.weg.application.service.PartidaService;
import br.com.weg.application.service.UsuarioService;
import br.com.weg.domain.service.IClubeService;
import br.com.weg.domain.service.IPartidaService;
import br.com.weg.domain.service.IUsuarioService;
import br.com.weg.presentation.view.AdminView;
import br.com.weg.presentation.view.ClubeView;
import br.com.weg.presentation.view.PartidaView;
import br.com.weg.presentation.view.UsuarioView;

public class MenuAdmin implements MenuStrategy{

    private IPartidaService partidaService = new PartidaService();
    private IUsuarioService usuarioService = new UsuarioService();
    private IClubeService clubeService = new ClubeService();
    ClubeView clubeView = new ClubeView();
    PartidaView partidaView = new PartidaView();
    UsuarioView usuarioView = new UsuarioView();
    AdminView adminView = new AdminView();


    @Override
    public void menu() {
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
