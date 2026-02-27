package br.com.weg;

/**
 * VIOLAÇÃO MONOLÍTICA DA INTERFACE SEGREGATION PRINCIPLE (ISP)
 *
 * Esta interface força TODAS as implementações a ter TODOS esses métodos,
 * mesmo que um Presidente nunca jogue futebol ou um Jogador nunca contrate ninguém.
 *
 * ISP viola = Forçar clientes a depender de métodos que não usam
 */
public interface MembroFutebol {

    // Métodos que só Jogadores usam
    void jogar();
    void treinar();
    void cobrarEscanteio();
    void marcarGol();
    void sofrerCartao();

    // Métodos que só Administradores/Presidentes usam
    void contratar();
    void demitir();
    void negocias();

    // Métodos gerais
    void ganharSalario();
    void receberBonus();
    void serfericado();

    // Métodos estranhos misturados
    void apareceNaImprensa();
    void assinarAutografo();
    void brigarNoTreino();
    void chorarNaDerrota();
    void dormir();
}
