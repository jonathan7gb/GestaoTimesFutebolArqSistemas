package br.com.weg.infra.strategies;


import br.com.weg.application.service.PartidaService;
import br.com.weg.application.service.UsuarioService;
import br.com.weg.domain.service.IPartidaService;
import br.com.weg.domain.service.IUsuarioService;
import br.com.weg.presentation.view.ClubeView;
import br.com.weg.presentation.view.PartidaView;
import br.com.weg.presentation.view.UsuarioView;

public class MenuComissao implements MenuStrategy{

    private IPartidaService partidaService = new PartidaService();
    private IUsuarioService usuarioService = new UsuarioService();
    ClubeView clubeView = new ClubeView();
    PartidaView partidaView = new PartidaView();
    UsuarioView usuarioView = new UsuarioView();


    @Override
    public void menu() {
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
}
