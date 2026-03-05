package br.com.weg.infra.notification;

import br.com.weg.domain.notification.INotificacao;

public class SilentNotificacao implements INotificacao {

    @Override
    public void sucesso(String mensagem) {
    }

    @Override
    public void erro(String mensagem) {
    }

    @Override
    public void info(String mensagem) {
    }
}
