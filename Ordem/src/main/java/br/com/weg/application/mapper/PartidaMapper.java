package br.com.weg.application.mapper;

import br.com.weg.application.dto.Partida.PartidaRequestDTO;
import br.com.weg.application.dto.Partida.PartidaResponseDTO;
import br.com.weg.domain.entity.Partida;

public class PartidaMapper {

    public PartidaResponseDTO partidaResponseDTO(Partida partida){
        return new PartidaResponseDTO(partida.getId(), partida.getIdClubeA(), partida.getIdClubeB(), partida.getDataHoraPartida(), partida.getLocalizacao());
    }

    public Partida toEntity(PartidaRequestDTO partidaRequestDTO){
        return new Partida(partidaRequestDTO.idClubeA(), partidaRequestDTO.idClubeB(), partidaRequestDTO.dataHoraPartida(), partidaRequestDTO.localizacao());
    }
}
