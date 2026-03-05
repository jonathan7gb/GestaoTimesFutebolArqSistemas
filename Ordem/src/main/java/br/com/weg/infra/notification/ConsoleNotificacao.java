package br.com.weg.infra.notification;

import br.com.weg.domain.notification.INotificacao;
import br.com.weg.presentation.view.helpers.MessageHelper;

/**
 * Estratégia concreta: exibe notificações coloridas no console.
 */
public class ConsoleNotificacao implements INotificacao {

    @Override
    public void sucesso(String mensagem) {
        MessageHelper.success(mensagem);
    }

    @Override
    public void erro(String mensagem) {
        MessageHelper.error(mensagem);
    }

    @Override
    public void info(String mensagem) {
        MessageHelper.info(mensagem);
    }
}
