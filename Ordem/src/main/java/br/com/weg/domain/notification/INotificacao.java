package br.com.weg.domain.notification;

/**
 * Padrão Strategy: interface que define o contrato de notificação.
 * Diferentes implementações podem ser trocadas em tempo de execução
 * sem alterar as classes que a utilizam (DIP / OCP).
 */
public interface INotificacao {
    void sucesso(String mensagem);
    void erro(String mensagem);
    void info(String mensagem);
}
