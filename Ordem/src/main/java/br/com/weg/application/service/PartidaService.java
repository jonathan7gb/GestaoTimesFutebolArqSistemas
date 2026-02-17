package br.com.weg.application.service;

import br.com.weg.application.dto.Partida.PartidaRequestDTO;
import br.com.weg.application.dto.Partida.PartidaResponseDTO;
import br.com.weg.application.mapper.PartidaMapper;
import br.com.weg.domain.entity.Partida;
import br.com.weg.domain.repository.PartidaRepository;
import br.com.weg.infra.persistence.PartidaRepositoryImpl;
import br.com.weg.presentation.view.helpers.MessageHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartidaService {

    PartidaRepository partidaRepository = new PartidaRepositoryImpl();
    PartidaMapper partidaMapper = new PartidaMapper();

    public PartidaResponseDTO criarPartida(PartidaRequestDTO partidaRequestDTO){
        Partida partida = partidaMapper.toEntity(partidaRequestDTO);

        try{
            if(partida == null){
                MessageHelper.error("Os dados da partida não pode ser nulos!\n");
                return null;
            }else{
                MessageHelper.success("Partida criada com sucesso!\n");
                partida = partidaRepository.criarPartida(partida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            MessageHelper.error("Erro ao cadastrar partida!\n");
        }
        return partidaMapper.toDto(partida);
    }

    public List<PartidaResponseDTO> listarPartidas(){
        List<PartidaResponseDTO> dtoList = new ArrayList<>();

        try{
                List<Partida> partidas = partidaRepository.listarPartidas();

                if(partidas.isEmpty()){
                    MessageHelper.error("Nenhuma partida encontrada!\n");
                }else{
                    for(Partida p : partidas){
                        dtoList.add(partidaMapper.toDto(p));
                    }
                }

        } catch (SQLException e) {
            e.printStackTrace();
            MessageHelper.error("Erro ao buscar dados das partidas!\n");
        }

        return dtoList;
    }

    public Map<Integer, String> retornarNomesClubesPartidas(){
        Map<Integer, String> NomesClube = new HashMap<>();
        try{
            NomesClube = partidaRepository.listarNomeClubes();
        } catch (SQLException e) {
            e.printStackTrace();
            MessageHelper.error("Erro ao listar as partidas");
        }
        return NomesClube;
    }
}
