package br.com.weg.presentation.controller;

import br.com.weg.infra.strategies.*;
import br.com.weg.presentation.view.AdminView;
import br.com.weg.presentation.view.helpers.MessageHelper;

public class MainController {

    private MenuStrategy menuStrategy;
    private AdminView adminView = new AdminView();

    public MainController() {}

    public void startSystem() {
        while (true) {
            int escolhaPrincipal = adminView.menuPrincipal();
            System.out.println();

            if (escolhaPrincipal == 0) {
                System.out.println("Saindo do sistema...");
                return;
            }

            changeMenu(escolhaPrincipal);

            if (this.menuStrategy != null) {
                executeMenu();
            } else {
                MessageHelper.error("Escolha inválida. Tente novamente.");
            }
        }
    }

    public void executeMenu() {
        this.menuStrategy.menu();
    }

    public void changeMenu(int opcao) {
        switch (opcao) {
            case 1 -> this.menuStrategy = new MenuJogador();
            case 2 -> this.menuStrategy = new MenuComissao();
            case 3 -> this.menuStrategy = new MenuPresidente();
            case 4 -> this.menuStrategy = new MenuAdmin();
            default -> this.menuStrategy = null;
        }
    }
}