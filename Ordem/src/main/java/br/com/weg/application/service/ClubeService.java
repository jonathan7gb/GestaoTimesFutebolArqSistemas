package br.com.weg.application.service;

import br.com.weg.application.dto.Clube.ClubeRequestDTO;
import br.com.weg.application.dto.Clube.ClubeResponseDTO;
import br.com.weg.application.mapper.ClubeMapper;
import br.com.weg.domain.entity.Clube;
import br.com.weg.domain.repository.ClubeRepository;
import br.com.weg.infra.persistence.ClubeRepositoryImpl;
import br.com.weg.presentation.view.helpers.MessageHelper;

import java.sql.SQLException;

public class ClubeService {

    ClubeMapper clubeMapper = new ClubeMapper();
    ClubeRepository clubeRepository = new ClubeRepositoryImpl();

    public ClubeResponseDTO criarClube(ClubeRequestDTO clubeRequestDTO){
        Clube clube = clubeMapper.toEntity(clubeRequestDTO);
        Clube clubeSalvo = null;
        try{
            clubeSalvo = clubeRepository.criarClube(clube);
        } catch (SQLException e ){
            MessageHelper.error("Erro ao salvar clube\n");
        }

        MessageHelper.success("Clube Cadastrado com Sucesso!\n");
        return clubeMapper.toDto(clubeSalvo);
    }
}
