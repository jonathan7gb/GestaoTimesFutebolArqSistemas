package br.com.weg.domain.service;

import br.com.weg.application.dto.Partida.PartidaRequestDTO;
import br.com.weg.application.dto.Partida.PartidaResponseDTO;

import java.util.List;
import java.util.Map;

/**
 * Interface de serviço para Partida.
 * Garante o DIP: o controlador depende desta abstração,
 * não da implementação concreta.
 */
public interface IPartidaService {
    PartidaResponseDTO criarPartida(PartidaRequestDTO partidaRequestDTO);
    List<PartidaResponseDTO> listarPartidas();
    List<PartidaResponseDTO> listarPartidasPorClube(int idClube);
    Map<Integer, String> retornarNomesClubesPartidas();
}
