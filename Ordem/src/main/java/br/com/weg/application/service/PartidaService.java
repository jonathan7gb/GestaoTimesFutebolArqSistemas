package br.com.weg.application.service;

import br.com.weg.application.dto.Partida.PartidaRequestDTO;
import br.com.weg.application.dto.Partida.PartidaResponseDTO;
import br.com.weg.application.mapper.PartidaMapper;
import br.com.weg.domain.entity.Partida;
import br.com.weg.domain.notification.INotificacao;
import br.com.weg.domain.repository.PartidaRepository;
import br.com.weg.domain.service.IPartidaService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serviço de Partida — camada de Aplicação.
 *
 * SOLID aplicado:
 *  S (SRP) — responsabilidade única: apenas regras de negócio de partida.
 *  D (DIP) — depende somente das abstrações PartidaRepository e INotificacao
 *             definidas no domínio; nenhuma referência a classes concretas
 *             de infraestrutura nesta camada.
 */
public class PartidaService implements IPartidaService {

    private final PartidaRepository partidaRepository;
    private final PartidaMapper partidaMapper = new PartidaMapper();
    private final INotificacao notificacao;

    /**
     * Construtor com injeção de dependências.
     * As implementações concretas são fornecidas pela camada de entrada (Main),
     * mantendo esta classe desacoplada de qualquer detalhe de infraestrutura.
     */
    public PartidaService(PartidaRepository partidaRepository, INotificacao notificacao) {
        this.partidaRepository = partidaRepository;
        this.notificacao = notificacao;
    }

    @Override
    public PartidaResponseDTO criarPartida(PartidaRequestDTO partidaRequestDTO) {
        Partida partida = partidaMapper.toEntity(partidaRequestDTO);

        try {
            if (partida == null) {
                notificacao.erro("Os dados da partida não pode ser nulos!\n");
                return null;
            } else {
                notificacao.sucesso("Partida criada com sucesso!\n");
                partida = partidaRepository.criarPartida(partida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            notificacao.erro("Erro ao cadastrar partida!\n");
        }
        return partidaMapper.toDto(partida);
    }

    @Override
    public List<PartidaResponseDTO> listarPartidas() {
        List<PartidaResponseDTO> dtoList = new ArrayList<>();

        try {
            List<Partida> partidas = partidaRepository.listarPartidas();

            if (partidas.isEmpty()) {
                notificacao.erro("Nenhuma partida encontrada!\n");
            } else {
                for (Partida p : partidas) {
                    dtoList.add(partidaMapper.toDto(p));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            notificacao.erro("Erro ao buscar dados das partidas!\n");
        }

        return dtoList;
    }

    @Override
    public List<PartidaResponseDTO> listarPartidasPorClube(int idClube) {
        try {
            return partidaRepository.buscarPartidaPorClube(idClube)
                    .stream()
                    .map(partidaMapper::toDto)
                    .toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, String> retornarNomesClubesPartidas() {
        Map<Integer, String> NomesClube = new HashMap<>();
        try {
            NomesClube = partidaRepository.listarNomeClubes();
        } catch (SQLException e) {
            e.printStackTrace();
            notificacao.erro("Erro ao listar as partidas");
        }
        return NomesClube;
    }
}
