package br.com.weg;


import br.com.weg.application.service.ClubeService;
import br.com.weg.application.service.PartidaService;
import br.com.weg.application.service.UsuarioService;
import br.com.weg.domain.notification.INotificacao;
import br.com.weg.infra.notification.ConsoleNotificacao;
import br.com.weg.infra.notification.SilentNotificacao;
import br.com.weg.infra.strategies.MenuStrategy;
import br.com.weg.presentation.controller.MainController;
import br.com.weg.presentation.view.helpers.MessageHelper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        MessageHelper.info("=== SISTEMA DE GESTÃO DE TIMES DE FUTEBOL ===");
        System.out.println("""
                | Selecione o modo de notificação:        |
                | 1 - Console (mensagens coloridas)       |
                | 2 - Silencioso (sem mensagens)          |
                """);
        System.out.print("| ? - SUA ESCOLHA: ");

        INotificacao notificacao;
        String escolha = sc.nextLine().trim();

        if ("2".equals(escolha)) {
            notificacao = new SilentNotificacao();
            MessageHelper.info("Estratégia: Modo Silencioso ativado.");
        } else {
            notificacao = new ConsoleNotificacao();
            MessageHelper.info("Estratégia: Modo Console ativado.");
        }

        ClubeService clubeService = new ClubeService();
        PartidaService partidaService = new PartidaService();
        UsuarioService usuarioService = new UsuarioService();

        clubeService.setNotificacao(notificacao);
        partidaService.setNotificacao(notificacao);
        usuarioService.setNotificacao(notificacao);

        MainController mainController = new MainController();

        mainController.startSystem();
    }
}