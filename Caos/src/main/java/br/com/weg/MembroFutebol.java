package br.com.weg;

//Violação do ISP: Nenhuma classe deve ser forçada a depender de métodos que não utiliza.
public interface MembroFutebol {
    void jogar();
    void treinar();
    void contratar();
    void demitir();
    void cobrarEscanteio();
}
