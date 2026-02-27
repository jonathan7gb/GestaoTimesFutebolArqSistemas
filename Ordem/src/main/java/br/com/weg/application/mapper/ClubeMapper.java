package br.com.weg.application.mapper;

import br.com.weg.application.dto.Clube.ClubeRequestDTO;
import br.com.weg.application.dto.Clube.ClubeResponseDTO;
import br.com.weg.domain.entity.Clube;

public class ClubeMapper {

    public ClubeResponseDTO toDto(Clube clube){
        return new ClubeResponseDTO(clube.getId(), clube.getNome(), clube.getAnoFundacao(), clube.getPais());
    }

    public Clube toEntity(ClubeRequestDTO clubeRequestDTO){
        return new Clube(clubeRequestDTO.nome(), clubeRequestDTO.anoFundacao(), clubeRequestDTO.pais());
    }
}
