package br.com.weg.infra.notification;

import br.com.weg.domain.notification.INotificacao;

/**
 * Estratégia concreta: suprime todas as notificações.
 * Útil para execução silenciosa ou cenários de teste automatizado.
 */
public class SilentNotificacao implements INotificacao {

    @Override
    public void sucesso(String mensagem) {
        // silencioso – sem saída
    }

    @Override
    public void erro(String mensagem) {
        // silencioso – sem saída
    }

    @Override
    public void info(String mensagem) {
        // silencioso – sem saída
    }
}
