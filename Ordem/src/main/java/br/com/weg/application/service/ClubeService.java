package br.com.weg.application.service;

import br.com.weg.application.dto.Clube.ClubeRequestDTO;
import br.com.weg.application.dto.Clube.ClubeResponseDTO;
import br.com.weg.application.mapper.ClubeMapper;
import br.com.weg.domain.entity.Clube;
import br.com.weg.domain.notification.INotificacao;
import br.com.weg.domain.repository.ClubeRepository;
import br.com.weg.domain.service.IClubeService;
import br.com.weg.infra.notification.ConsoleNotificacao;
import br.com.weg.infra.persistence.ClubeRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClubeService implements IClubeService {

    private final ClubeMapper clubeMapper = new ClubeMapper();
    private final ClubeRepository clubeRepository;
    private INotificacao notificacao;

    public ClubeService() {
        this.clubeRepository = new ClubeRepositoryImpl();
        this.notificacao = new ConsoleNotificacao();
    }

    public ClubeService(ClubeRepository clubeRepository, INotificacao notificacao) {
        this.clubeRepository = clubeRepository;
        this.notificacao = notificacao;
    }

    public void setNotificacao(INotificacao notificacao) {
        this.notificacao = notificacao;
    }

    @Override
    public ClubeResponseDTO criarClube(ClubeRequestDTO clubeRequestDTO) {
        Clube clube = clubeMapper.toEntity(clubeRequestDTO);
        Clube clubeSalvo = null;
        try {
            clubeSalvo = clubeRepository.criarClube(clube);
            notificacao.sucesso("Clube Cadastrado com Sucesso!\n");
        } catch (SQLException e) {
            notificacao.erro("Erro ao salvar clube\n");
        }

        if (clubeSalvo == null) return null;
        return clubeMapper.toDto(clubeSalvo);
    }

    @Override
    public List<ClubeResponseDTO> mostrarClubes() {
        List<Clube> clubeList = new ArrayList<>();
        List<ClubeResponseDTO> clubeResponseDTOList = new ArrayList<>();
        try {
            clubeList = clubeRepository.listarClubes();

            if (clubeList.isEmpty()) {
                notificacao.erro("Nenhum clube encontrado!");
            }
        } catch (SQLException e) {
            notificacao.erro("Erro ao retornar os dados de clubes.");
        }

        for (Clube c : clubeList) {
            clubeResponseDTOList.add(clubeMapper.toDto(c));
        }
        return clubeResponseDTOList;
    }
}
