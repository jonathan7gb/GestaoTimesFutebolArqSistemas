package br.com.weg.domain.service;

import br.com.weg.application.dto.Clube.ClubeRequestDTO;
import br.com.weg.application.dto.Clube.ClubeResponseDTO;

import java.util.List;

/**
 * Interface de serviço para Clube.
 * Garante o DIP: o controlador depende desta abstração,
 * não da implementação concreta.
 */
public interface IClubeService {
    ClubeResponseDTO criarClube(ClubeRequestDTO clubeRequestDTO);
    List<ClubeResponseDTO> mostrarClubes();
}
