package br.com.weg;

import br.com.weg.application.service.ClubeService;
import br.com.weg.application.service.PartidaService;
import br.com.weg.application.service.UsuarioService;
import br.com.weg.domain.notification.INotificacao;
import br.com.weg.infra.notification.ConsoleNotificacao;
import br.com.weg.infra.notification.SilentNotificacao;
import br.com.weg.infra.persistence.ClubeRepositoryImpl;
import br.com.weg.infra.persistence.PartidaRepositoryImpl;
import br.com.weg.infra.persistence.UsuarioRepositoryImpl;
import br.com.weg.presentation.controller.MainController;
import br.com.weg.presentation.view.helpers.MessageHelper;

import java.util.Scanner;

/**
 * Ponto de entrada da aplicação.
 *
 * É aqui — e somente aqui — que as implementações concretas da camada de
 * infraestrutura são instanciadas e injetadas nas abstrações do domínio,
 * completando o fluxo de Inversão de Dependências (DIP):
 *
 *   Presentation → Application (via IXxxService)
 *   Application  → Domain      (via XxxRepository / INotificacao)
 *   Infra        implementa as interfaces do domínio
 *   Main         conecta tudo, sem poluir nenhuma camada intermediária
 */
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

        // Padrão Strategy (OCP + DIP): a estratégia de notificação é escolhida
        // em tempo de execução; adicionar um novo modo não exige alterar os serviços.
        INotificacao notificacao;
        String escolha = sc.nextLine().trim();

        if ("2".equals(escolha)) {
            notificacao = new SilentNotificacao();
            MessageHelper.info("Estratégia: Modo Silencioso ativado.");
        } else {
            notificacao = new ConsoleNotificacao();
            MessageHelper.info("Estratégia: Modo Console ativado.");
        }

        // Injeção de dependências via construtor: os serviços recebem abstrações,
        // nunca instanciam diretamente nada da camada de infraestrutura.
        ClubeService clubeService = new ClubeService(new ClubeRepositoryImpl(), notificacao);
        PartidaService partidaService = new PartidaService(new PartidaRepositoryImpl(), notificacao);
        UsuarioService usuarioService = new UsuarioService(new UsuarioRepositoryImpl(), notificacao);

        MainController mainController = new MainController(clubeService, partidaService, usuarioService);

        mainController.startSystem();
    }
}